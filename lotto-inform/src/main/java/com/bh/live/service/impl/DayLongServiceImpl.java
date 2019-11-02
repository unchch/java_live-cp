package com.bh.live.service.impl;

import com.bh.live.common.helper.NumberHandlerHelper;
import com.bh.live.common.utils.ExpectUtil;
import com.bh.live.dao.DayLongDao;
import com.bh.live.model.inform.Omission;
import com.bh.live.pojo.res.inform.DragonRes;
import com.bh.live.service.DayLongService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * @author Administrator
 * @title: DayLongServiceImpl
 * @projectName live
 * @description: TODO
 * @date 2019/6/13  16:52
 */
@Service
public class DayLongServiceImpl<main> implements DayLongService {
    @Resource
    private DayLongDao dayLongDao;

    /**
     * 获取所有所需数据
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getStatDayLongList(@Param("param") Map<String, Object> param) {
        String ldType = param.get("ldType").toString();
        if (ldType.equals("1")) {
            param.put("ldType", "大");
        }
        if (ldType.equals("2")) {
            param.put("ldType", "小");
        }
        if (ldType.equals("3")) {
            param.put("ldType", "单");
        }
        if (ldType.equals("4")) {
            param.put("ldType", "双");
        }
        if (ldType.equals("5")) {
            param.put("ldType", "龙");
        }
        if (ldType.equals("6")) {
            param.put("ldType", "虎");
        }
        List<DragonRes> dayLongList = dayLongDao.getStatDayLongList(param);
        Integer maxLdPeriod = dayLongDao.getMaxLdPeriod(param);//最大期数
        List<Integer> asList = new ArrayList<>();
        //定义数组用于填充空值
        if (maxLdPeriod != null && maxLdPeriod > 0) {
            Integer[] arr = new Integer[maxLdPeriod - 1];
            arr[0] = 2;
            for (int i = 1; i < arr.length; i++) {
                arr[i] = i + 2;
            }
            asList = Arrays.asList(arr);
        }
        List<Integer> ldList = new ArrayList<>(asList);
        String openDate = null; //时间
        String ldPeriod = null; //期数
        Integer frequency = 0;  //次数
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> newMap = null;
        if (dayLongList != null && !dayLongList.isEmpty()) {
            for (int i = 0; i < dayLongList.size(); i++) {
                openDate = dayLongList.get(i).getOpenDate();
                ldPeriod = dayLongList.get(i).getLdPeriod();
                frequency = dayLongList.get(i).getFrequency();
                //重新组装数据传给前台展示
                if (newMap == null || !newMap.containsKey("openDate")) {
                    newMap = Maps.newHashMap();
                    newMap.put("openDate", openDate);
                    newMap.put(ldPeriod, frequency);
                    list.add(newMap);
                } else {
                    //如果map中不存在这个时间的数据 加进去
                    if (!newMap.get("openDate").equals(openDate)) {
                        newMap = Maps.newHashMap();
                        newMap.put("openDate", openDate);
                        newMap.put(ldPeriod, frequency);
                        list.add(newMap);
                    } else {
                        //存在的话就加期数和次数
                        newMap.put(ldPeriod, frequency);

                    }
                }
            }
            //填充空值
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    if (ldList != null && !ldList.isEmpty()) {
                        for (Integer ld : ldList) {
                            if (!list.get(i).containsKey(ld.toString())) {
                                list.get(i).put(ld.toString(), null);
                            }

                        }

                    }
                }
            }
        }
        return list;
    }

	@Override
	public List<Object> getStatDayLongList(String varietyType, String ball, String type) {
		
		List<String> filed =NumberHandlerHelper.getBallFiled(Integer.parseInt(ball));
		LocalDateTime nowDay = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String str =numberBigSmall(varietyType, ball,filed.get(0));
		List<Object> list = Lists.newArrayList();
		if("bigChangLong".equals(type)){
			//20天数据，每天统计
		    for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, "+str+" from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"大",varietyType);
			}
		    return list;
		}else if("smallChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, "+str+" from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"小",varietyType);
			}
		    return list;
		}else if("singleChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if("+filed.get(0)+"%2=0,'双','单') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"单",varietyType);
			}
		    return list;
		}else if("doubleChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if("+filed.get(0)+"%2=0,'双','单') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"双",varietyType);
			}
		    return list;
		}else if("dragonChangLong".equals(type)){
			filed =NumberHandlerHelper.getDragonTigerFiled(Integer.parseInt(ball));
			if(filed.get(0).isEmpty()){
				return list;
			}
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if("+filed.get(0)+"='龙','龙','虎') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"龙",varietyType);
			}
		    return list;
		}else if("tigerChangLong".equals(type)){
			filed =NumberHandlerHelper.getDragonTigerFiled(Integer.parseInt(ball));
			if(filed.get(0).isEmpty()){
				return list;
			}
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if("+filed.get(0)+"='龙','龙','虎') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"虎",varietyType);
			}
		    return list;
		}else if("wdChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if(RIGHT("+filed.get(0)+",1)>4,'大','小') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"大",varietyType);
			}
		    return list;
		}else if("wxChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if(RIGHT("+filed.get(0)+",1)>4,'大','小') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"小",varietyType);
			}
		    return list;
		}else if("hdChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, IF(LENGTH("+filed.get(0)+")=1,IF("+filed.get(0)+"%2=0,'双','单'),IF(LEFT("+filed.get(0)+",1)+RIGHT("+filed.get(0)+",1)%2=0,'双','单' )) bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"单",varietyType);
			}
		    return list;
		}else if("hsChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, IF(LENGTH("+filed.get(0)+")=1,IF("+filed.get(0)+"%2=0,'双','单'),IF(LEFT("+filed.get(0)+",1)+RIGHT("+filed.get(0)+",1)%2=0,'双','单' )) bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"双",varietyType);
			}
		    return list;
		}else if("zChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if("+filed.get(0)+" in(2,3,5,7,11,13,17,19),'质','和') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"质",varietyType);
			}
		    return list;
		}else if("hChangLong".equals(type)){
			for (int i = 0; i < 20; i++) {
		    	Map<Long, Integer> map = Maps.newHashMap();
		    	LocalDateTime  today = nowDay.minusDays(i);
				LocalDateTime  toDay = nowDay.minusDays(i-1);
				String	startDate = df.format(today)+" 05:00:00";
				String	endDate= df.format(toDay)+" 05:00:00";
		    	String sql= "select expect,open_time, if("+filed.get(0)+" in (2,3,5,7,11,13,17,19),'质','和') bigSmall from t_variety_current WHERE open_time>= '"+startDate+"' and open_time <= '"+endDate+"' and variety_type = #{varietyType} order by open_time desc";
		    	List<Omission> historyOmission = dayLongDao.getStatDayLongList(varietyType, sql);
		    	daLongExpect(sdf, list, map, historyOmission,"和",varietyType);
			}
		    return list;
		}

		return null;
	}

	private void daLongExpect(SimpleDateFormat sdf, List<Object> list, Map<Long, Integer> map,
			List<Omission> historyOmission,String str,String varietyType) {
		int temp = 0;
		List<Object> flag = Lists.newArrayList();
		for (int j = 0; j < historyOmission.size(); j++) {
			if(!str.equals(historyOmission.get(j).getBigSmall())){
				long expect = ExpectUtil.expectGap(historyOmission.get(temp).getExpect()+"",""+historyOmission.get(j).getExpect(), varietyType)-1;
				//key期数 ，value次数
				if(expect>=2) {
					map.put(expect, map.get(expect) == null ? 1 : map.get(expect) + 1);
				}
				temp=j;
			}
		}
		//早上未开奖，有可能为空
		if(!historyOmission.isEmpty()){
			flag.add(sdf.format(historyOmission.get(historyOmission.size()-1).getOpenTime()));
			flag.add(map);
			list.add(flag);
		}
	}

	public  String numberBigSmall(String varietyType,String ball,String faield) {
    	String sql = "";
    	int num =0;
		if("0".equals(varietyType) || "1".equals(varietyType)){
			num=5;
			if("11".equals(ball)){num=11;}
			sql ="CASE WHEN "+faield+"> "+num+" then'大' WHEN  "+faield+"<= "+num+" then '小'  END bigSmall";
		}else if("2".equals(varietyType)||"3".equals(varietyType)||"7".equals(varietyType)||"10".equals(varietyType)){
			num=4;
			if("11".equals(ball)){num=22;}
			sql ="CASE WHEN "+faield+"> "+num+" then'大' WHEN  "+faield+"<= "+num+" then '小'  END bigSmall";
		}else if("4".equals(varietyType)){
			
			num=10;
			sql ="CASE WHEN "+faield+"> "+num+" then'大' WHEN  "+faield+"<= "+num+" then '小'  END bigSmall";
		}else if("5".equals(varietyType)|| "8".equals(varietyType)){
			
			num=10;
			sql ="CASE WHEN "+faield+"> "+num+" then'大' WHEN  "+faield+"<= "+num+" then '小'   END bigSmall";
			if("11".equals(ball)){
				num=84;
				sql ="CASE WHEN "+faield+"> "+num+" then'大' WHEN  "+faield+"< "+num+" then '小' WHEN "+faield+"="+num+" then '和'  END bigSmall";
			}
		}else if("6".equals(varietyType)){
			num=810;
			sql ="CASE WHEN "+faield+"> "+num+" then'大' WHEN  "+faield+"< "+num+" then '小' WHEN "+faield+"="+num+" then '和'  END bigSmall";
		}else if("9".equals(varietyType)){
			num=6;
			if("11".equals(ball)){num=30;}
			sql ="CASE WHEN "+faield+"> "+num+" then'大' WHEN  "+faield+"< "+num+" then '小' WHEN "+faield+"="+num+" then '和'  END bigSmall";
		}
		return sql;
	}
}
