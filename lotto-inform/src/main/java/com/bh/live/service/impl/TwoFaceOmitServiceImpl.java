package com.bh.live.service.impl;

import com.bh.live.common.helper.PackageOmissionDataHelper;
import com.bh.live.dao.TwoFaceOmitDao;
import com.bh.live.model.inform.Omission;
import com.bh.live.service.TwoFaceOmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TwoFaceOmitServiceImpl implements TwoFaceOmitService {
    @Resource
    private TwoFaceOmitDao twoFaceOmitDao;
    private final String[] crownNum = {"大", "小", "单", "双"};
    private final List<String> twoFace = Arrays.asList(crownNum);

    @Override
    public List<Omission> getCurrentOmission(int varietyType) {
        List<Omission> returnList = new ArrayList<>();
        for (String dxds : twoFace) {
            Long count = 0L;
            String field = "";
            String condition = "";
            if (dxds.equals("大") || dxds.equals("小")) {
                field = "big_small as crownSubDesc";
                condition = "big_small = '" + dxds + "'";
            }
            if (dxds.equals("单") || dxds.equals("双")) {
                field = "single_double as crownSubDesc";
                condition = "single_double = '" + dxds + "'";
            }

            List<Omission> crownSubOmissions = twoFaceOmitDao.currentOmission(varietyType, field, condition);
            Omission topExpect = crownSubOmissions.get(0);//最新一期
            Omission secondExpect = crownSubOmissions.get(1);//开出当前冠亚和的最新一期
            if (!topExpect.getCrownSubDesc().equals(dxds)) {
                //如果最新一期不是当前冠亚和，开出当前冠亚和的最新一期减去最新一期的值为当前遗漏
                count = topExpect.getExpect() - secondExpect.getExpect();
            } else {
                //如果是，返回-1
                count += -1;
                for (int i = 1; i < crownSubOmissions.size(); i++) {
                    //判断期数是否是连续开出当前冠亚和
                    if (!((i + 1) == crownSubOmissions.size())) {
                        if (topExpect.getExpect() - i == crownSubOmissions.get(i + 1).getExpect()) {
                            //如果是，继续加-1
                            count += -1;
                        }
                    }
                }
            }
            Omission currentOmission = new Omission();
            currentOmission.setCrownSubDesc("冠亚和" + dxds);
            currentOmission.setCurrentOmi(count);
            returnList.add(currentOmission);
        }
        return returnList;
    }

    @Override
    public List<Omission> getTodayOmiss(int varietyType) {
        String condition = varietyType == 0 ? " and left(expect,8) = DATE_FORMAT(NOW(),'%Y%m%d') " : " and open_time BETWEEN CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') and CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 23:59:59') ";

        String sql = "SELECT tv.expect,tv.big_small,tv.single_double FROM `t_variety_current` tv where variety_type = #{varietyType} \n" +
                condition + " order by expect desc ";
        List<Omission> todayOmission = twoFaceOmitDao.getDateOmission(varietyType, sql, 0);
        return PackageOmissionDataHelper.getTwoFaceOmissions(todayOmission, twoFace, "today");
    }

    @Override
    public List<Omission> getThisWeekOmiss(int varietyType) {
        String condition = varietyType == 0 ? " and YEARWEEK(str_to_date(left(tv.expect,8), '%Y%m%d') - INTERVAL 1 DAY)= yearweek(now() - INTERVAL 1 DAY) " : " and YEARWEEK(date_format(open_time,'%Y-%m-%d') - INTERVAL 1 DAY) = yearweek(now() - INTERVAL 1 DAY) ";

        String sql = "select expect,big_small,single_double from \n" +
                "(SELECT tv.expect,tv.big_small,tv.single_double FROM `t_variety_current` tv where \n" +
                "variety_type = #{varietyType} " + condition +
                " order by open_time desc) a\n";
        List<Omission> omissionList = twoFaceOmitDao.getDateOmission(varietyType, sql, 0);
        return PackageOmissionDataHelper.getTwoFaceOmissions(omissionList, twoFace, "week");
    }

    @Override
    public List<Omission> getThisMonthOmiss(int varietyType) {
        String condition = varietyType == 0 ? " and str_to_date(left(tv.expect,8), '%Y%m%d') BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') " : " and tv.open_time BETWEEN CONCAT(date_format(LAST_DAY(now()),'%Y-%m-'),'01 00:00:00') and CONCAT(LAST_DAY(now()),' 23:59:59') ";

        String sql = "select expect,big_small,single_double from \n" +
                "(SELECT tv.expect,tv.big_small,tv.single_double FROM `t_variety_current` tv where \n" +
                "variety_type = #{varietyType} " + condition +
                " order by open_time desc) a\n";
        List<Omission> omissionList = twoFaceOmitDao.getDateOmission(varietyType, sql, 0);
        return PackageOmissionDataHelper.getTwoFaceOmissions(omissionList, twoFace, "month");
    }

    @Override
    public List<Omission> todayAppear(int varietyType) {
        String[] array = {"大", "小", "单", "双"};
        List<String> four = new ArrayList<>(Arrays.asList(array));
        String timeCondition = varietyType == 0 ? " and left(expect,8) = DATE_FORMAT(NOW(),'%Y%m%d') " : " and open_time BETWEEN CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') and CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 23:59:59')";
        List<Omission> appearCount = twoFaceOmitDao.getAppearCount(varietyType, timeCondition);
        for (Omission omission : appearCount) {
            for (int i = 0; i < four.size(); i++) {
                if (omission.getCrownSubDesc().equals(four.get(i))) {
                    four.remove(i);
                }
            }
            omission.setCrownSubDesc("冠亚和" + omission.getCrownSubDesc());
        }
        for (String s : four) {
            Omission omission = new Omission();
            omission.setCrownSubDesc("冠亚和" + s);
            omission.setTodayAppear(0L);
            appearCount.add(omission);
        }
        return appearCount;
    }

    @Override
    public List<Omission> historyMiss(int varietyType) {
        String sql = "SELECT tv.big_small,tv.single_double FROM `t_variety_current` tv where " +
                "variety_type = #{varietyType} order by open_time desc";
        List<Omission> omissionList = twoFaceOmitDao.getDateOmission(varietyType, sql, 0);
        return PackageOmissionDataHelper.getTwoFaceOmissions(omissionList, twoFace, "history");
    }

    @Override
    public List<Omission> getOmissionCount(int varietyType) {
        return PackageOmissionDataHelper.MergeList(
                PackageOmissionDataHelper.MergeList(
                        PackageOmissionDataHelper.MergeList(
                                PackageOmissionDataHelper.MergeList(
                                        PackageOmissionDataHelper.MergeList(
                                                getCurrentOmission(varietyType), getTodayOmiss(varietyType)),
                                        getThisWeekOmiss(varietyType)), getThisMonthOmiss(varietyType)),
                        todayAppear(varietyType)), historyMiss(varietyType));
    }
}
