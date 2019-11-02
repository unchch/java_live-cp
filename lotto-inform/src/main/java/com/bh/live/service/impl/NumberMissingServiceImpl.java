package com.bh.live.service.impl;

import com.bh.live.common.helper.CurrentOmissionHelper;
import com.bh.live.common.helper.NumberHandlerHelper;
import com.bh.live.common.helper.PackageOmissionDataHelper;
import com.bh.live.dao.NumberMissingDao;
import com.bh.live.model.inform.Omission;
import com.bh.live.service.NumberMissingService;
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
public class NumberMissingServiceImpl implements NumberMissingService {
    @Resource
    private NumberMissingDao numberMissingDao;

    @Override
    public List<Omission> getCurrentOmission(Integer number, int varietyType) {
        List<Omission> omissions = numberMissingDao.currentOmission(varietyType, number, NumberHandlerHelper.getBallFiled(number).get(0));
        //查询结果分组
        Map<Integer, List<Omission>> collect = omissions.stream().collect(
                Collectors.groupingBy(Omission::getNumberBall));
        //获取最新开出的一条
        //Omission topOne = numberMissingDao.getTopOne(varietyType, NumberHandlerHelper.getBallFiled(number).get(0));
        Omission topOne = omissions.get(0);
        Map<Integer, List<Omission>> resMap = new HashMap<>();
        for (Map.Entry<Integer, List<Omission>> entry : collect.entrySet()) {
            //System.out.println(entry.getKey()+"---"+entry.getValue());
            resMap.put(entry.getKey(), CurrentOmissionHelper.getCurrentOmission(varietyType, entry.getKey(), entry.getValue(), topOne));
        }
        List<Omission> resList = new ArrayList<>();
        for (Map.Entry<Integer, List<Omission>> entry : resMap.entrySet()) {
            resList.addAll(entry.getValue());
        }
        return resList;
    }

    @Override
    public List<Omission> getTodayOmiss(Integer number, int varietyType) {
        String condition = varietyType != 1 ? " and left(expect,8) = DATE_FORMAT(NOW(),'%Y%m%d') " : " and open_time BETWEEN CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') and CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 23:59:59') ";

        String sql = "(SELECT expect," + NumberHandlerHelper.getBallFiled(number).get(0) + " numberBall FROM t_variety_current WHERE variety_type = #{varietyType} " +
                condition + " order by open_time desc)";
        List<Omission> todayOmission = numberMissingDao.getDateOmission(varietyType, sql, null);
        return CurrentOmissionHelper.getPackageOmissionCount(varietyType, todayOmission, "today", null, Arrays.asList(NumberHandlerHelper.getVarietyNumber(varietyType)));
    }

    @Override
    public List<Omission> getThisWeekOmiss(Integer number, int varietyType) {
        String condition = varietyType != 1 ? " and YEARWEEK(str_to_date(left(tv.expect,8), '%Y%m%d') - INTERVAL 1 DAY)= yearweek(now() - INTERVAL 1 DAY) " : " and YEARWEEK(date_format(open_time,'%Y-%m-%d') - INTERVAL 1 DAY) = yearweek(now() - INTERVAL 1 DAY) ";
        String sql = "(SELECT tv.expect," + NumberHandlerHelper.getBallFiled(number).get(0) + " numberBall FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} " + condition + "order by open_time desc)";
        List<Omission> todayOmission = numberMissingDao.getDateOmission(varietyType, sql, null);

        Map<Integer, List<Omission>> resMap = new HashMap<>();
        List<Integer> numberList = Arrays.asList(NumberHandlerHelper.getVarietyNumber(varietyType));

        //查询上一个日期最新的一条(必须)
        String sql2 = "select tv.expect,open_time," + NumberHandlerHelper.getBallFiled(number).get(0) + " numberBall FROM `t_variety_current` tv where \n" +
                "variety_type = #{varietyType} and " + NumberHandlerHelper.getBallFiled(number).get(0) + " = #{number} and \n" +
                "expect not in (SELECT tv.expect FROM `t_variety_current` tv where variety_type = #{varietyType} " +
                condition + ") " +
                "order by open_time desc limit 1";
        for (Integer integer : numberList) {
            List<Omission> omissions = numberMissingDao.getDateOmission(varietyType, sql2, integer);
            resMap.put(integer, omissions);
        }

        return CurrentOmissionHelper.getPackageOmissionCount(varietyType, todayOmission, "week", resMap, numberList);
    }

    @Override
    public List<Omission> getThisMonthOmiss(Integer number, int varietyType) {
        String condition = varietyType != 1 ? " and str_to_date(left(tv.expect,8), '%Y%m%d') BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') " : " and tv.open_time BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') ";
        String sql = "(SELECT tv.expect," + NumberHandlerHelper.getBallFiled(number).get(0) + " numberBall FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} " + condition +
                " order by open_time desc)";
        List<Omission> todayOmission = numberMissingDao.getDateOmission(varietyType, sql, null);
        Map<Integer, List<Omission>> resMap = new HashMap<>();
        List<Integer> numberList = Arrays.asList(NumberHandlerHelper.getVarietyNumber(varietyType));

        //查询上一个日期最新的一条(必须)
        String sql2 = "select tv.expect,open_time," + NumberHandlerHelper.getBallFiled(number).get(0) + " numberBall FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} and " + NumberHandlerHelper.getBallFiled(number).get(0) + " = #{number} and expect not in" +
                "(SELECT tv.expect FROM `t_variety_current` tv where variety_type = #{varietyType} " +
                condition + " order by open_time desc) " + "order by open_time desc limit 1";

        for (Integer integer : numberList) {
            List<Omission> omissions = numberMissingDao.getDateOmission(varietyType, sql2, integer);
            resMap.put(integer, omissions);
        }

        return CurrentOmissionHelper
                .getPackageOmissionCount(varietyType, todayOmission, "month", resMap, numberList);
    }

    @Override
    public List<Omission> todayAppear(Integer number, int varietyType, String dateType) {

        return numberMissingDao.getAppearCount(varietyType, number, dateType, NumberHandlerHelper.getBallFiled(number).get(0));
    }

    @Override
    public List<Omission> historyMiss(Integer number, int varietyType) {
        //String condition = varietyType != 1 ? " and str_to_date(left(tv.expect,8), '%Y%m%d') BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') " : " and tv.open_time BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') ";
        String sql = "SELECT tv.expect," + NumberHandlerHelper.getBallFiled(number).get(0) + " numberBall FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} order by open_time desc";
        List<Omission> todayOmission = numberMissingDao.getDateOmission(varietyType, sql, null);
        Map<Integer, List<Omission>> resMap = new HashMap<>();
        List<Integer> numberList = Arrays.asList(NumberHandlerHelper.getVarietyNumber(varietyType));
        //查询上一个日期最新的一条(必须)
        /*String sql2 = "select tv.expect,open_time," + NumberHandlerHelper.getBallFiled(number) + " numberBall FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} and " + NumberHandlerHelper.getBallFiled(number) + " = #{number} and expect not in" +
                "(SELECT tv.expect FROM `t_variety_current` tv where variety_type = #{varietyType} " +
                condition + " order by open_time desc) " + "order by open_time desc limit 1";

        for (Integer integer : numberList) {
            List<Omission> omissions = numberMissingDao.getDateOmission(varietyType, sql2, integer);
            resMap.put(integer, omissions);
        }*/
        return CurrentOmissionHelper
                .getPackageOmissionCount(varietyType, todayOmission, "history", resMap, numberList);
    }

    @Override
    public List<Omission> getOmissionCount(Integer number, int varietyType) {
        List<Omission> todayOmiss = getTodayOmiss(number, varietyType);
        List<Omission> todayAppear = todayAppear(number, varietyType, "today");
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
