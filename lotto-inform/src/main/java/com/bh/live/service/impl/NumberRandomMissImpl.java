package com.bh.live.service.impl;

import com.bh.live.common.helper.AllOmissionHelper;
import com.bh.live.common.helper.NumberHandlerHelper;
import com.bh.live.common.helper.PackageOmissionDataHelper;
import com.bh.live.common.utils.CombinationsUtil;
import com.bh.live.common.utils.DateUtil;
import com.bh.live.common.utils.ExpectUtil;
import com.bh.live.dao.NumberRandomMissDao;
import com.bh.live.model.inform.Omission;
import com.bh.live.service.NumRandomMissService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @title: NumberMissingServiceImpl
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/25  16:32
 */
@Service
public class NumberRandomMissImpl implements NumRandomMissService {
    @Resource
    private NumberRandomMissDao randomMissDao;


    @Override
    public List<Omission> getCurrentOmission(Integer number, int varietyType) {
        List<Omission> returnList = new ArrayList<>();
        //如果没有选择条件第几个球号，默认查询所有球的数据
            String fieldCondition = "";
            StringBuffer stringBuffer = new StringBuffer();
            List<Omission> currentOmissions = null;
            for (int i = 1; i <= 2; i++) {
                String ifStr = "if(" + NumberHandlerHelper.getCrownSubFiled(i).get(0) + "='双','双','单') " + NumberHandlerHelper.getCrownSubFiled(i).get(1) + ",";
                stringBuffer.append(ifStr);
            }
            fieldCondition = stringBuffer.substring(0, stringBuffer.length() - 1);
            currentOmissions = randomMissDao.currentOmission(varietyType, fieldCondition);
           // List<Omission> currentOmission = AllOmissionHelper.getCurrentOmission(varietyType, currentOmissions, currentOmissions.get(0));


            List<Omission> currentOmission = new ArrayList<>();
            Map<String, List<Omission>> oneBallCollect = currentOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberOneBall));
            for (Map.Entry<String, List<Omission>> entry : oneBallCollect.entrySet()) {
                Long omissions1 = AllOmissionHelper.getOmissions(NumberHandlerHelper.getFieldValue(currentOmissions.get(0), 1, null), entry.getKey(), varietyType, currentOmissions.get(0), entry.getValue());
                returnList.add(AllOmissionHelper.packageCurrrentOmission(omissions1,  entry.getKey()));
            }
            Map<String, List<Omission>> twoBallCollect = currentOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberTwoBall));
            for (Map.Entry<String, List<Omission>> entry : twoBallCollect.entrySet()) {
                Long omissions1 = AllOmissionHelper.getOmissions(NumberHandlerHelper.getFieldValue(currentOmissions.get(0), 2, null), entry.getKey(), varietyType, currentOmissions.get(0), entry.getValue());
                returnList.add(AllOmissionHelper.packageCurrrentOmission(omissions1, entry.getKey()));
            }
            
            returnList.addAll(currentOmission);
        return returnList;
    }

    @Override
    public List<Omission> getTodayOmiss(Integer number, int varietyType) {
        String timeCondition = varietyType != 1 ? " and left(expect,8) = DATE_FORMAT(NOW(),'%Y%m%d') " : " and open_time BETWEEN CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') and CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 23:59:59') ";
        return getAllOmissions(varietyType, number, timeCondition, "today", "omission");
    }

    private List<Omission> getAllOmissions(int varietyType, Integer number, String timeCondition, String dateType, String flag) {
        String fieldStr = "";
            fieldStr = "if(single_double='双','双','单') singleDouble, if(big_small='双','双','单') bigSmall";
            String sql = "(SELECT expect," + fieldStr + " FROM t_variety_current WHERE variety_type = #{varietyType} " +
                    timeCondition + " order by open_time desc)";
            List<Omission> todayOmission = randomMissDao.getDateOmission(varietyType, sql, null);
            if (flag.equals("todayAppear")) {
                return todayOmission;
            }
            List<String> singleDouble = Lists.transform(todayOmission, omission -> omission.getSingleDouble());//获取单双的集合
            Map<String, Integer> twoFaceMaxOmission = PackageOmissionDataHelper.getTwoFaceMaxOmission(singleDouble);
            
            List<String> bigSmall = Lists.transform(todayOmission, omission -> omission.getBigSmall());//获取大小的集合
            Map<String, Integer> bigSmallOmission = PackageOmissionDataHelper.getTwoFaceMaxOmission(bigSmall);
            
            List<Omission> singleDoubos =PackageSingleDoubleOmission(number, varietyType, twoFaceMaxOmission, dateType);
            List<Omission> bigSmalls =PackageSingleDoubleOmission(number, varietyType, bigSmallOmission, dateType);
            List<Omission> omissions = PackageOmissionDataHelper.MergeList(singleDoubos,bigSmalls);
            return omissions;
    }
    public static List<Omission> PackageSingleDoubleOmission(Integer number, int varietyType, Map<String, Integer> twoFaceMaxOmission, String dateType) {
        List<Omission> omissionList = new ArrayList<>();
        for (Map.Entry<String, Integer> entries : twoFaceMaxOmission.entrySet()) {
            Omission currentOmission = new Omission();
            currentOmission.setSingleDouble("总和" + entries.getKey());
            if (dateType.equals("today")) {
                currentOmission.setTodayOmi(entries.getValue().longValue());
            }
            if (dateType.equals("week")) {
                currentOmission.setThisWeekOmi(entries.getValue().longValue());
            }
            if (dateType.equals("month")) {
                currentOmission.setThisMonthOmi(entries.getValue().longValue());
            }
            if (dateType.equals("history")) {
                currentOmission.setHistoryOmi(entries.getValue().longValue());
            }
            omissionList.add(currentOmission);
        }
        return omissionList;
    }
    @Override
    public List<Omission> getThisWeekOmiss(Integer number, int varietyType) {
        String condition = varietyType != 1 ? " and YEARWEEK(str_to_date(left(expect,8), '%Y%m%d') - INTERVAL 1 DAY)= yearweek(now() - INTERVAL 1 DAY) " : " and YEARWEEK(date_format(open_time,'%Y-%m-%d') - INTERVAL 1 DAY) = yearweek(now() - INTERVAL 1 DAY) ";
        return getAllOmissions(varietyType, number, condition, "week", "omission");
    }

    @Override
    public List<Omission> getThisMonthOmiss(Integer number, int varietyType) {
        String condition = varietyType != 1 ? " and str_to_date(left(expect,8), '%Y%m%d') BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') " : " and open_time BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') ";
        return getAllOmissions(varietyType, number, condition, "month", "omission");
    }

    @Override
    public List<Omission> todayAppear(Integer number, int varietyType) {
        String timeCondition = varietyType != 1 ? " and left(expect,8) = DATE_FORMAT(NOW(),'%Y%m%d') " : " and open_time BETWEEN CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') and CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 23:59:59') ";
        List<Omission> allOmissions = getAllOmissions(varietyType, number, timeCondition, "history", "todayAppear");
        //number为空查询所有位置的球号
            List<Omission> resList = new ArrayList<>();
            Map<String, List<Omission>> oneCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getSingleDouble));
            resList.addAll(getReturnList(1, varietyType, oneCollect));
            Map<String, List<Omission>> twoCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getBigSmall));
            resList.addAll(getReturnList(2, varietyType, twoCollect));
           
            return resList;

    }

    private List<Omission> getReturnList(Integer number, int varietyType, Map<String, List<Omission>> collect) {
        List<Omission> resList = new ArrayList<>();
        for (Map.Entry<String, List<Omission>> entry : collect.entrySet()) {
            Omission omission = new Omission();
            omission.setSingleDouble("总和"+ entry.getKey());
            omission.setTodayAppear(new Long(entry.getValue().size()));
            resList.add(omission);
        }
        return resList;
    }

    @Override
    public List<Omission> historyMiss(Integer number, int varietyType) {
        //String condition = varietyType != 1 ? " and str_to_date(left(expect,8), '%Y%m%d') BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') " : " and = open_time BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') ";
        return getAllOmissions(varietyType, number, "", "history", "omission");
    }

    @Override
    public List<Omission> getOmissionCount(Integer number, int varietyType) {
        List<Omission> todayOmiss = getTodayOmiss(number, varietyType);
        List<Omission> todayAppear = todayAppear(number, varietyType);
        List<Omission> thisWeekOmiss = getThisWeekOmiss(number, varietyType);
        List<Omission> thisMonthOmiss = getThisMonthOmiss(number, varietyType);
        List<Omission> currentOmission = getCurrentOmission(number, varietyType);
        List<Omission> historyMiss = historyMiss(number, varietyType);
        List<Omission> omissions = PackageOmissionDataHelper.MergeList(
                PackageOmissionDataHelper.MergeList(
                        PackageOmissionDataHelper.MergeList(
                                PackageOmissionDataHelper.MergeList(
                                        PackageOmissionDataHelper.MergeList(
                                                todayOmiss, todayAppear),
                                        thisWeekOmiss), thisMonthOmiss),
                        currentOmission), historyMiss);
        return omissions;
    }
    
    @Override
    public List<Omission> getRandom(Integer number, int varietyType,int num) {
    	CombinationsUtil com = new CombinationsUtil(Arrays.asList(NumberHandlerHelper.getVarietyNumber(varietyType)),num);
    	List<Omission> omissions = Lists.newArrayList();
    	
        String historySql ="select expect,open_time," + NumberHandlerHelper.getBallFiled(number).get(0) + " numberBall from t_variety_current WHERE variety_type = #{varietyType} order by open_time desc";
        
        Date nowStart =DateUtil.getTimesmorning();
        Date weekStart =DateUtil.getTimesWeekmorning();
        Date mothStart =DateUtil.getTimesMonthmorning();
        
        List<Omission> historyOmission = randomMissDao.getDateOmission(varietyType, historySql, null);
    	while (com.iterator().hasNext()) {
    		ArrayList<Integer> random = (ArrayList<Integer>) com.iterator().next();
    		Omission omis= new Omission();
    		omis.setTodayAppear(0L);
    		omis.setTodayOmi(0L);
    		omis.setThisWeekOmi(0L);
    		omis.setThisMonthOmi(0L);
    		omis.setHistoryOmi(0L);
    		omis.setCurrentOmi(0L);
    		Map<String,Integer> cly = Maps.newHashMap();
    		cly.put("todayOmiss",0);cly.put("weekOmiss",0);cly.put("mothOmiss",0);cly.put("historyOmiss",0);cly.put("currentOmiz",0);cly.put("currentOmif",0);
    		
			for (int i = 0; i < historyOmission.size(); i++) {
				if(!random.contains(historyOmission.get(i).getNumberBall())){
					cly = numberOmissing(varietyType, historyOmission, nowStart, weekStart, mothStart, omis,cly, i);
					
				}else{
					if(historyOmission.get(i).getOpenTime().getTime()>=nowStart.getTime()){
						omis.setTodayAppear(omis.getTodayAppear()+1);//今日出现次数
					}
					cly.put("currentOmif", 1);
					//当前遗漏
					//第二种情况  遗漏次数
					if(cly.get("currentOmiz")==0){
						int expect = ExpectUtil.expectGap(historyOmission.get(i).getExpect()+"",""+historyOmission.get(0).getExpect(), varietyType+"")-1;
						omis.setCurrentOmi((long)expect);
					}
				}
			}
			
			omis.setSingleDouble(random.toString().substring(1, random.toString().length()-1));;
			omissions.add(omis);
    	}
        
        return omissions;
    }

	@Override
	public List<Omission> getTotalTwoFace(int varietyType) {
    	
        String historySql ="select expect,open_time,single_double,big_small from t_variety_current WHERE variety_type = #{varietyType} order by open_time desc";
        List<Omission> historyOmission = randomMissDao.getDateOmission(varietyType, historySql, null);
        List<String> twoData = Lists.newArrayList("大","小","单","双");
        List<Omission> omissions= missingNumber(varietyType, historyOmission, twoData);
		return omissions;
	}

	/**
	 * 大小单双遗漏
	 * */
	private List<Omission> missingNumber(int varietyType,  List<Omission> historyOmission,
			List<String> twoData) {
		List<Omission> omissions= Lists.newArrayList();
		Date nowStart =DateUtil.getTimesmorning();
        Date weekStart =DateUtil.getTimesWeekmorning();
        Date mothStart =DateUtil.getTimesMonthmorning();
        
        for (int j = 0; j < twoData.size(); j++) {
        	Omission omis= new Omission();
        	omis.setTodayAppear(0L);
        	omis.setTodayOmi(0L);
        	omis.setThisWeekOmi(0L);
        	omis.setThisMonthOmi(0L);
        	omis.setHistoryOmi(0L);
        	omis.setCurrentOmi(0L);
        	Map<String,Integer> num = Maps.newHashMap();
        	num.put("todayOmiss",0);num.put("weekOmiss",0);num.put("mothOmiss",0);num.put("historyOmiss",0);num.put("currentOmiz",0);num.put("currentOmif",0);
        	for (int i = 0; i < historyOmission.size(); i++) {
        		if(j<=1){
					if(!twoData.get(j).equals(historyOmission.get(i).getBigSmall())){
						num=numberOmissing(varietyType, historyOmission, nowStart, weekStart, mothStart, omis, num,  i);
					}else{
						if(historyOmission.get(i).getOpenTime().getTime()>=nowStart.getTime()){
							omis.setTodayAppear(omis.getTodayAppear()+1);//今日出现次数
						}
						num.put("currentOmif",1);
						if(num.get("currentOmiz")==0){
							int expect = ExpectUtil.expectGap(historyOmission.get(0).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")-1;
							omis.setCurrentOmi((long)expect);
						}
					}
        		}else{
        			if(!twoData.get(j).equals(historyOmission.get(i).getSingleDouble())){
        				num= numberOmissing(varietyType, historyOmission, nowStart, weekStart, mothStart, omis, num, i);
					}else{
						if(historyOmission.get(i).getOpenTime().getTime()>=nowStart.getTime()){
							omis.setTodayAppear(omis.getTodayAppear()+1);//今日出现次数
						}
						num.put("currentOmif",1);
						if(num.get("currentOmiz")==0){
							int expect = ExpectUtil.expectGap(historyOmission.get(0).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")+1;
							omis.setCurrentOmi((long)expect);
						}
					}
        		}
			}
        	omis.setSingleDouble(twoData.get(j));;
			omissions.add(omis);
			
		}
        return omissions;
	}

	private Map<String,Integer> numberOmissing(int varietyType, List<Omission> historyOmission, Date nowStart, Date weekStart,
			Date mothStart, Omission omis, Map<String,Integer> num, int i) {
		//时间大于今天开始时间  -- 今天的数据
		if(historyOmission.get(i).getOpenTime().getTime()>=nowStart.getTime()){
			//今日最大遗漏期数
			int expect = ExpectUtil.expectGap(historyOmission.get(num.get("todayOmiss")).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")+1;
			if(omis.getTodayOmi()<expect){
				omis.setTodayOmi((long)expect);
				num.put("todayOmiss", i);
			}
		}
		if(historyOmission.get(i).getOpenTime().getTime()>=weekStart.getTime()){//本周的数据
			int expect = ExpectUtil.expectGap(historyOmission.get(num.get("weekOmiss")).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")+1;
			//本周的最大遗漏期数遗漏
			if(omis.getThisMonthOmi()<expect){
				omis.setThisWeekOmi((long)expect);
				num.put("weekOmiss", i);
			}
		}
		if(historyOmission.get(i).getOpenTime().getTime()>=mothStart.getTime()){//本月的数据
			int expect = ExpectUtil.expectGap(historyOmission.get(num.get("mothOmiss")).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")+1;
			//本月的最大遗漏期数遗漏
			if(omis.getThisMonthOmi()<expect){
				omis.setThisMonthOmi((long)expect);
				num.put("mothOmiss", i);
			}
		}
		num.put("currentOmiz", 1);
		//当前遗漏
		//第二种情况  遗漏次数
		if(num.get("currentOmif")==0){
			int expect = ExpectUtil.expectGap(historyOmission.get(0).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")+1;
			omis.setCurrentOmi((long)expect);
		}
		
		//历史的数据
		int expect = ExpectUtil.expectGap(historyOmission.get(num.get("historyOmiss")).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")+1;
		//历史的最大遗漏期数遗漏
		if(omis.getHistoryOmi()<expect){
			omis.setHistoryOmi((long)expect);
			num.put("historyOmiss", i);
		}
		return num;
	}

	@Override
	public List<Omission> getTotalBigSmall(int varietyType) {
		String sql = "select expect,open_time,big_small,single_double from t_variety_current where variety_type = #{varietyType} order by open_time desc";
		List<Omission> historyOmission = randomMissDao.getDateOmission(varietyType, sql, null);
		List<String> twoData = Lists.newArrayList("大","小");
		return missingNumber(varietyType, historyOmission, twoData);
	}

	@Override
	public List<Omission> getDiceCount(int varietyType) {
		String sql = "select expect,open_time,crown_sub bigSmall from t_variety_current where variety_type = #{varietyType} order by open_time desc";
		List<Omission> historyOmission = randomMissDao.getDateOmission(varietyType, sql, null);
		List<Integer> num =Arrays.asList(NumberHandlerHelper.getVarietyNumber(varietyType));
		List<String> twoData = Lists.newArrayList();
		for (int i = 0; i < num.size(); i++) {
			twoData.add(""+num.get(i));
		}
		return missingOneNumber(varietyType, historyOmission, twoData);
	}
	
	/**
	 * 查询bigSmall的遗漏
	 * */
	private List<Omission> missingOneNumber(int varietyType,  List<Omission> historyOmission,
			List<String> twoData) {
		List<Omission> omissions= Lists.newArrayList();
		Date nowStart =DateUtil.getTimesmorning();
        Date weekStart =DateUtil.getTimesWeekmorning();
        Date mothStart =DateUtil.getTimesMonthmorning();
        
        for (int j = 0; j < twoData.size(); j++) {
        	Omission omis= new Omission();
        	omis.setTodayAppear(0L);
        	omis.setTodayOmi(0L);
        	omis.setThisWeekOmi(0L);
        	omis.setThisMonthOmi(0L);
        	omis.setHistoryOmi(0L);
        	omis.setCurrentOmi(0L);
        	Map<String,Integer> num = Maps.newHashMap();
        	num.put("todayOmiss",0);num.put("weekOmiss",0);num.put("mothOmiss",0);num.put("historyOmiss",0);num.put("currentOmiz",0);num.put("currentOmif",0);
        	for (int i = 0; i < historyOmission.size(); i++) {
					if(!twoData.get(j).equals(historyOmission.get(i).getBigSmall())){
						num=numberOmissing(varietyType, historyOmission, nowStart, weekStart, mothStart, omis, num,  i);
					}else{
						if(historyOmission.get(i).getOpenTime().getTime()>=nowStart.getTime()){
							omis.setTodayAppear(omis.getTodayAppear()+1);//今日出现次数
						}
						num.put("currentOmif",1);
						if(num.get("currentOmiz")==0){
							int expect = ExpectUtil.expectGap(historyOmission.get(i).getExpect()+"",""+historyOmission.get(0).getExpect(), varietyType+"")-1;
							omis.setCurrentOmi((long)expect);
						}
					}
        	
			}
        	omis.setSingleDouble(twoData.get(j));;
			omissions.add(omis);
			
		}
        return omissions;
	}

	@Override
	public List<Omission> getShortCard(int varietyType) {
		String sql = "select expect,open_time,open_code bigSmall from t_variety_current where variety_type = #{varietyType} order by open_time desc";
		List<Omission> historyOmission = randomMissDao.getDateOmission(varietyType, sql, null);
		List<String> twoData = Lists.newArrayList("1,1","2,2","3,3","4,4","5,5","6,6");
        return openCodeMissing(varietyType, historyOmission, twoData);
	}

	private List<Omission>  openCodeMissing(int varietyType, List<Omission> historyOmission, List<String> twoData) {
		Date nowStart =DateUtil.getTimesmorning();
        Date weekStart =DateUtil.getTimesWeekmorning();
        Date mothStart =DateUtil.getTimesMonthmorning();
        List<Omission> omissions = Lists.newArrayList();
        for (int j = 0; j < twoData.size(); j++) {
        	Omission omis= new Omission();
        	omis.setTodayAppear(0L);
        	omis.setTodayOmi(0L);
        	omis.setThisWeekOmi(0L);
        	omis.setThisMonthOmi(0L);
        	omis.setHistoryOmi(0L);
        	omis.setCurrentOmi(0L);
        	Map<String,Integer> num = Maps.newHashMap();
        	num.put("todayOmiss",0);num.put("weekOmiss",0);num.put("mothOmiss",0);num.put("historyOmiss",0);num.put("currentOmiz",0);num.put("currentOmif",0);
        	String[] regex = twoData.get(j).split(",");
        	for (int i = 0; i < historyOmission.size(); i++) {
				if(!(historyOmission.get(i).getBigSmall().contains(regex[0]) && historyOmission.get(i).getBigSmall().contains(regex[1]))){
					num=numberOmissing(varietyType, historyOmission, nowStart, weekStart, mothStart, omis, num,  i);
				}else{
					if(historyOmission.get(i).getOpenTime().getTime()>=nowStart.getTime()){
						omis.setTodayAppear(omis.getTodayAppear()+1);//今日出现次数
					}
					num.put("currentOmif",1);
					if(num.get("currentOmiz")==0){
						int expect = ExpectUtil.expectGap(historyOmission.get(0).getExpect()+"",""+historyOmission.get(i).getExpect(), varietyType+"")-1;
						omis.setCurrentOmi((long)expect);
					}
				}
			}
        	omis.setSingleDouble(twoData.get(j));;
			omissions.add(omis);
			
		}
        return omissions;
	}

	@Override
	public List<Omission> getLongCard(int varietyType) {
		String sql = "select expect,open_time,open_code bigSmall from t_variety_current where variety_type = #{varietyType} order by open_time desc";
		List<Omission> historyOmission = randomMissDao.getDateOmission(varietyType, sql, null);
		Integer[] num={1,2,3,4,5,6};
		List<String> twoData = Lists.newArrayList();
		CombinationsUtil com = new CombinationsUtil(Arrays.asList(num),2);
		while (com.iterator().hasNext()) {
			String str =com.iterator().next().toString();
			twoData.add(str.substring(1, str.length()-1).replace(" ", ""));
		}
        return openCodeMissing(varietyType, historyOmission, twoData);
	}

	@Override
	public List<Omission> getTheArmy(int varietyType) {
		String sql = "select expect,open_time,open_code bigSmall from t_variety_current where variety_type = #{varietyType} order by open_time desc";
		List<Omission> historyOmission = randomMissDao.getDateOmission(varietyType, sql, null);
		List<String> twoData = Lists.newArrayList("1,1,1","2,2,2","3,3,3","4,4,4","5,5,5","6,6,6");
        return missingOneNumber(varietyType, historyOmission, twoData);
	}
}
