package com.bh.live.service.impl;

import com.bh.live.common.helper.AllOmissionHelper;
import com.bh.live.common.helper.NumberHandlerHelper;
import com.bh.live.common.helper.PackageOmissionDataHelper;
import com.bh.live.dao.NumberBigSmallMissDao;
import com.bh.live.model.inform.Omission;
import com.bh.live.service.NumberBigSmallMissService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @title: NumberMissingServiceImpl
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/25  16:32
 */
@Service
public class NumberBigSmallMissImpl implements NumberBigSmallMissService {
    @Resource
    private NumberBigSmallMissDao bigSmallMissDao;

    @Override
    public List<Omission> getCurrentOmission(Integer number, int varietyType) {
        List<Omission> returnList = new ArrayList<>();
        //如果没有选择条件第几个球号，默认查询所有球的数据
        if (number == null) {
            String fieldCondition = "";
            StringBuffer stringBuffer = new StringBuffer();
            List<Omission> currentOmissions = null;
            if (varietyType == 0 || varietyType == 1) {
                for (int i = 1; i <= 10; i++) {
                    String ifStr = "if(" + NumberHandlerHelper.getBallFiled(i).get(0) + "<=5,'小','大') " + NumberHandlerHelper.getBallFiled(i).get(1) + ",";
                    stringBuffer.append(ifStr);
                }
                fieldCondition = stringBuffer.substring(0, stringBuffer.length() - 1);
                currentOmissions = bigSmallMissDao.currentOmission(varietyType, fieldCondition);
                List<Omission> currentOmission = AllOmissionHelper.getCurrentOmission(varietyType, currentOmissions, currentOmissions.get(0));
                returnList.addAll(currentOmission);
            } else {
                for (int i = 1; i <= 5; i++) {
                    String ifStr = "if(" + NumberHandlerHelper.getBallFiled(i).get(0) + "<=5,'小','大') " + NumberHandlerHelper.getBallFiled(i).get(1) + ",";
                    stringBuffer.append(ifStr);
                }
                fieldCondition = stringBuffer.substring(0, stringBuffer.length() - 1);
                currentOmissions = bigSmallMissDao.currentOmission(varietyType, fieldCondition);
                List<Omission> currentOmission = AllOmissionHelper.getCurrentOmission(varietyType, currentOmissions, currentOmissions.get(0));
                returnList.addAll(currentOmission);
            }
        } else {
            //如果选择了球号的情况
            //List<Omission> list = new ArrayList<>();
            String fieldCondition = "if(" + NumberHandlerHelper.getBallFiled(number).get(0) + "<=5,'小','大')  singleDouble";
            List<Omission> currentOmissions = bigSmallMissDao.currentOmission(varietyType, fieldCondition);

            Omission topExpect = currentOmissions.get(0);//最新一期
            Map<String, List<Omission>> collect = currentOmissions.stream().collect(Collectors.groupingBy(Omission::getSingleDouble));

            for (Map.Entry<String, List<Omission>> entry : collect.entrySet()) {
                Long singleDouble = AllOmissionHelper.getOmissions(NumberHandlerHelper.getFieldValue(topExpect, number, "singleDouble"), entry.getKey(), varietyType, topExpect, entry.getValue());
                Omission currentOmission = new Omission();
                currentOmission.setSingleDouble(NumberHandlerHelper.getNumberDesc(varietyType, number) + entry.getKey());
                currentOmission.setCurrentOmi(singleDouble);
                returnList.add(currentOmission);
            }
        }
        return returnList;
    }

    @Override
    public List<Omission> getTodayOmiss(Integer number, int varietyType) {
        String timeCondition = varietyType != 1 ? " and left(expect,8) = DATE_FORMAT(NOW(),'%Y%m%d') " : " and open_time BETWEEN CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') and CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 23:59:59') ";
        return getAllOmissions(varietyType, number, timeCondition, "today", "omission");
    }

    private List<Omission> getAllOmissions(int varietyType, Integer number, String timeCondition, String dateType, String flag) {
        String fieldStr = "";
        if (number == null) {
            StringBuffer stringBuffer = new StringBuffer();
            List<Omission> currentOmissions = null;
            if (varietyType == 0 || varietyType == 1) {
                for (int i = 1; i <= 10; i++) {
                    String ifStr = "if(" + NumberHandlerHelper.getBallFiled(i).get(0) + "<=5,'小','大') " + NumberHandlerHelper.getBallFiled(i).get(1) + ",";
                    stringBuffer.append(ifStr);
                }
                fieldStr = stringBuffer.substring(0, stringBuffer.length() - 1);
                String sql = "(SELECT expect," + fieldStr + " FROM t_variety_current WHERE variety_type = #{varietyType} " +
                        timeCondition + " order by open_time desc)";
                currentOmissions = bigSmallMissDao.getDateOmission(varietyType, sql, null);
                if (flag.equals("todayAppear")) {
                    return currentOmissions;
                }
                List<Omission> currentOmission = AllOmissionHelper.getDayOmiss(varietyType, currentOmissions, dateType);
                return currentOmission;
            } else {
                for (int i = 1; i <= 5; i++) {
                    String ifStr = "if(" + NumberHandlerHelper.getBallFiled(i).get(0) + "<=5,'小','大') " + NumberHandlerHelper.getBallFiled(i).get(1) + ",";
                    stringBuffer.append(ifStr);
                }
                fieldStr = stringBuffer.substring(0, stringBuffer.length() - 1);
                String sql = "(SELECT expect," + fieldStr + " FROM t_variety_current WHERE variety_type = #{varietyType} " +
                        timeCondition + " order by open_time desc)";
                currentOmissions = bigSmallMissDao.getDateOmission(varietyType, sql, null);
                if (flag.equals("todayAppear")) {
                    return currentOmissions;
                }
                List<Omission> currentOmission = AllOmissionHelper.getDayOmiss(varietyType, currentOmissions, dateType);
                return currentOmission;
            }
        } else {

            fieldStr = "if(" + NumberHandlerHelper.getBallFiled(number).get(0) + "<=5,'小','大') singleDouble";
            String sql = "(SELECT expect," + fieldStr + " FROM t_variety_current WHERE variety_type = #{varietyType} " +
                    timeCondition + " order by open_time desc)";
            List<Omission> todayOmission = bigSmallMissDao.getDateOmission(varietyType, sql, null);
            if (flag.equals("todayAppear")) {
                return todayOmission;
            }
            List<String> singleDouble = Lists.transform(todayOmission, omission -> omission.getSingleDouble());//获取单双的集合
            Map<String, Integer> twoFaceMaxOmission = PackageOmissionDataHelper.getTwoFaceMaxOmission(singleDouble);
            return AllOmissionHelper.PackageSingleDoubleOmission(number, varietyType, twoFaceMaxOmission, dateType);
        }
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
        if (number == null) {
            List<Omission> resList = new ArrayList<>();
            Map<String, List<Omission>> oneCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberOneBall));
            resList.addAll(getReturnList(1, varietyType, oneCollect));
            Map<String, List<Omission>> twoCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberTwoBall));
            resList.addAll(getReturnList(2, varietyType, twoCollect));
            Map<String, List<Omission>> threeCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberThreeBall));
            resList.addAll(getReturnList(3, varietyType, threeCollect));
            Map<String, List<Omission>> fourCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberFourBall));
            resList.addAll(getReturnList(4, varietyType, fourCollect));
            Map<String, List<Omission>> fiveCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberFiveBall));
            resList.addAll(getReturnList(5, varietyType, fiveCollect));
            if (varietyType == 0 || varietyType == 1) {
                Map<String, List<Omission>> sixCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberSixBall));
                resList.addAll(getReturnList(6, varietyType, sixCollect));
                Map<String, List<Omission>> sevenCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberSevenBall));
                resList.addAll(getReturnList(7, varietyType, sevenCollect));
                Map<String, List<Omission>> eightCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberEightBall));
                resList.addAll(getReturnList(8, varietyType, eightCollect));
                Map<String, List<Omission>> nineCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberNineBall));
                resList.addAll(getReturnList(9, varietyType, nineCollect));
                Map<String, List<Omission>> tenCollect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getNumberTenBall));
                resList.addAll(getReturnList(10, varietyType, tenCollect));
            }
            return resList;
        } else {
            Map<String, List<Omission>> collect = allOmissions.stream().collect(Collectors.groupingBy(Omission::getSingleDouble));
            return getReturnList(number, varietyType, collect);
        }

    }

    private List<Omission> getReturnList(Integer number, int varietyType, Map<String, List<Omission>> collect) {
        List<Omission> resList = new ArrayList<>();
        for (Map.Entry<String, List<Omission>> entry : collect.entrySet()) {
            Omission omission = new Omission();
            
            omission.setSingleDouble(NumberHandlerHelper.getNumberDesc(varietyType, number) + entry.getKey());
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

}
