package com.bh.live.service.impl;

import com.bh.live.common.helper.AllOmissionHelper;
import com.bh.live.common.helper.NumberHandlerHelper;
import com.bh.live.common.helper.PackageOmissionDataHelper;
import com.bh.live.dao.NumberOmitDao;
import com.bh.live.model.inform.Omission;
import com.bh.live.service.NumberOmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NumberOmitServiceImpl implements NumberOmitService {
    @Resource
    private NumberOmitDao numberOmitDao;
    private final Integer[] crownNum = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
    private final List<Integer> crowns = Arrays.asList(crownNum);

    @Override
    public List<Omission> getCurrentOmission(int varietyType) {
        List<Omission> crownSubOmissions = numberOmitDao.currentOmission(varietyType);
        //查询结果分组
        Map<Integer, List<Omission>> collect = crownSubOmissions.stream().collect(
                Collectors.groupingBy(Omission::getCrownSub));
        //Omission topOne = numberOmitDao.getTopOne(varietyType);
        Omission topOne = crownSubOmissions.get(0);
        Map<Integer, List<Omission>> resMap = new HashMap<>();
        for (Map.Entry<Integer, List<Omission>> entry : collect.entrySet()) {
            //System.out.println(entry.getKey()+"---"+entry.getValue());
            resMap.put(entry.getKey(), subCurrentOmission(varietyType, entry.getKey(), entry.getValue(), topOne));
        }
        List<Omission> resList = new ArrayList<>();
        for (Map.Entry<Integer, List<Omission>> entry : resMap.entrySet()) {
            resList.addAll(entry.getValue());
        }
        return resList;
    }

    private List<Omission> subCurrentOmission(int varietyType, Integer key, List<Omission> omissions, Omission topExpect) {
        List<Omission> returnList = new ArrayList<>();
        Long count = AllOmissionHelper.getOmissions(NumberHandlerHelper.getFieldValue(topExpect, key, "crownSub"), key.toString(), varietyType, topExpect, omissions);
        /*if (!(topExpect.getCrownSub().compareTo(key) == 0)) {
            //如果最新一期不是当前冠亚和，开出当前冠亚和的最新一期减去最新一期的值为当前遗漏
            if (varietyType == 1) {
                count = topExpect.getExpect() - secondExpect.getExpect();
            } else {
                count = Long.valueOf(CalcOmit.subExpect(varietyType, topExpect.getExpect().toString(), secondExpect.getExpect().toString()));
            }
        } else {
            //如果是，返回-1
            count += -1;
            for (int i = 1; i < omissions.size(); i++) {
                //判断期数是否是连续开出当前冠亚和
                if (!((i + 1) == omissions.size())) {
                    if (varietyType == 1) {
                        if (topExpect.getExpect() - i == omissions.get(i + 1).getExpect()) {
                            //如果是，继续加-1
                            count += -1;
                        }
                    } else {
                        if (CalcOmit.subExpect(varietyType, topExpect.getExpect().toString(), omissions.get(i + 1).getExpect().toString()) == 0) {
                            //如果是，继续加-1
                            count += -1;
                        }
                    }
                }
            }
        }*/
        Omission currentOmission = new Omission();
        currentOmission.setCrownSub(key);
        currentOmission.setCurrentOmi(count);
        returnList.add(currentOmission);
        return returnList;
    }

    @Override
    public List<Omission> getTodayOmiss(int varietyType) {
        String condition = varietyType == 0 ? " and left(expect,8) = DATE_FORMAT(NOW(),'%Y%m%d') " : " and open_time BETWEEN CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') and CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 23:59:59') ";

        String sql = "(SELECT expect, crown_sub crownSub FROM t_variety_current WHERE variety_type = #{varietyType} " +
                condition + " order by open_time desc)";
        List<Omission> todayOmission = numberOmitDao.getDateOmission(varietyType, sql, 0);
        return PackageOmissionDataHelper.getPackageOmissionCount(varietyType, todayOmission, "today", new HashMap<>(), crowns);
    }

    @Override
    public List<Omission> getThisWeekOmiss(int varietyType) {
        String condition = varietyType == 0 ? " and YEARWEEK(str_to_date(left(tv.expect,8), '%Y%m%d') - INTERVAL 1 DAY)= yearweek(now() - INTERVAL 1 DAY) " : " and YEARWEEK(date_format(open_time,'%Y-%m-%d') - INTERVAL 1 DAY) = yearweek(now() - INTERVAL 1 DAY) ";
        String sql = "(SELECT tv.expect,tv.crown_sub crownSub FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} " + condition + "order by open_time desc)";
        List<Omission> todayOmission = numberOmitDao.getDateOmission(varietyType, sql, 0);

        Map<Integer, List<Omission>> resMap = new HashMap<>();
        //查询上一个日期最新的一条(必须)
        String sql2 = "select tv.expect,open_time,tv.crown_sub crownSub FROM `t_variety_current` tv where \n" +
                "variety_type = #{varietyType} and crown_sub = #{crownSub} and \n" +
                "expect not in (SELECT tv.expect FROM `t_variety_current` tv where variety_type = #{varietyType} " +
                condition + ") " +
                "order by open_time desc limit 1";
        for (Integer integer : crowns) {
            List<Omission> omissions = numberOmitDao.getDateOmission(varietyType, sql2, integer);
            resMap.put(integer, omissions);
        }
        return PackageOmissionDataHelper.getPackageOmissionCount(varietyType, todayOmission, "week", resMap, crowns);
    }

    @Override
    public List<Omission> getThisMonthOmiss(int varietyType) {
        String condition = varietyType == 0 ? " and str_to_date(left(tv.expect,8), '%Y%m%d') BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') " : " and tv.open_time BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') ";
        String sql = "(SELECT tv.expect,tv.crown_sub crownSub FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} " + condition +
                " order by open_time desc)";
        List<Omission> todayOmission = numberOmitDao.getDateOmission(varietyType, sql, 0);
        //查询上一个日期最新的一条(必须)
        String sql2 = "select tv.expect,open_time,tv.crown_sub crownSub FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} and crown_sub = #{crownSub} and expect not in" +
                "(SELECT tv.expect FROM `t_variety_current` tv where variety_type = #{varietyType} " +
                condition + " order by open_time desc) " + "order by open_time desc limit 1";

        Map<Integer, List<Omission>> resMap = new HashMap<>();
        for (Integer integer : crowns) {
            List<Omission> omissions = numberOmitDao.getDateOmission(varietyType, sql2, integer);
            resMap.put(integer, omissions);
        }
        return PackageOmissionDataHelper
                .getPackageOmissionCount(varietyType, todayOmission, "month", resMap, crowns);
    }

    @Override
    public List<Omission> todayAppear(int varietyType, String dateType) {
        return numberOmitDao.getAppearCount(varietyType, dateType);
    }

    @Override
    public List<Omission> historyMiss(int varietyType) {
        List<Omission> crownSubOmissions = numberOmitDao.historyOmission(varietyType);
        //获取一共开出了多少期
        Long maxExpect = crownSubOmissions.stream().collect(Collectors.summingLong(Omission::getHistoryOmi));
        for (Omission omission : crownSubOmissions) {
            omission.setHistoryOmi(maxExpect - omission.getHistoryOmi());
        }
        return crownSubOmissions;
    }

    @Override
    public List<Omission> getOmissionCount(int varietyType) {
        //今日遗漏
        List<Omission> todayOmission = getTodayOmiss(varietyType);
        //今日出现次数
        List<Omission> todayAppear = todayAppear(varietyType, "today");
        //本周出现次数
        List<Omission> weekOmission = todayAppear(varietyType, "week");
        //本月出现次数
        List<Omission> monthOmission = todayAppear(varietyType, "month");
        //当前遗漏
        List<Omission> currentOmission = getCurrentOmission(varietyType);

        for (Omission crownSubOmission : weekOmission) {
            //本周/本月出现次数小于2的情况，向前一个日期找上一次出现这个冠亚和
            if (crownSubOmission.getTodayAppear() < 2) {
                List<Omission> maxCrownSub = numberOmitDao.getMaxCrownSub(varietyType, crownSubOmission.getCrownSub());
                Long maxOmission = PackageOmissionDataHelper.packageOmissionCount(maxCrownSub, varietyType); //获取最大遗漏
                crownSubOmission.setThisWeekOmi(maxOmission);
            }
        }
        //本周遗漏
        List<Omission> thisWeekOmiss = getThisWeekOmiss(varietyType);
        for (Omission wekOmission : weekOmission) {
            for (Omission crownSubOmission : thisWeekOmiss) {
                if (wekOmission.getCrownSub().compareTo(crownSubOmission.getCrownSub()) == 0 && wekOmission.getTodayAppear() < 2) {
                    crownSubOmission.setThisWeekOmi(wekOmission.getThisWeekOmi());
                }
            }
        }
        for (Omission crownSubOmission : monthOmission) {
            if (crownSubOmission.getTodayAppear() < 2) {
                List<Omission> maxCrownSub = numberOmitDao.getMaxCrownSub(varietyType, crownSubOmission.getCrownSub());
                Long maxOmission = PackageOmissionDataHelper.packageOmissionCount(maxCrownSub,varietyType); //获取最大遗漏
                crownSubOmission.setThisMonthOmi(maxOmission);
            }
        }
        //本月遗漏
        List<Omission> thisMonthOmiss = getThisMonthOmiss(varietyType);
        for (Omission monOmission : monthOmission) {
            for (Omission crownSubOmission : thisMonthOmiss) {
                if (monOmission.getCrownSub().compareTo(crownSubOmission.getCrownSub()) == 0 && monOmission.getTodayAppear() < 2) {
                    crownSubOmission.setThisMonthOmi(monOmission.getThisMonthOmi());
                }
            }
        }
        //合并今日遗漏，今日出现次数，本周遗漏，本月遗漏list
        List<Omission> crownSubOmissions = PackageOmissionDataHelper.MergeList(
                PackageOmissionDataHelper.MergeList(
                        PackageOmissionDataHelper.MergeList(
                                PackageOmissionDataHelper.MergeList(
                                        PackageOmissionDataHelper.MergeList(
                                                todayOmission, todayAppear),
                                        thisWeekOmiss), thisMonthOmiss),
                        currentOmission), historyMiss(varietyType));

        return crownSubOmissions;
    }

}
