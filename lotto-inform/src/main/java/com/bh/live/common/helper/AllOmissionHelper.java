package com.bh.live.common.helper;/**
 * Create by Administrator ON 2019/7/4
 */

import com.bh.live.common.TrendUtils.CalcOmit;
import com.bh.live.model.inform.Omission;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2019/7/4 19:56
 * @desc
 * @Version 1.0
 */
public class AllOmissionHelper {

    /**
     * 计算所有球号的今日遗漏
     *
     * @param varietyType
     * @param omissions
     * @param topExpect
     * @return
     */
    public static List<Omission> getCurrentOmission(int varietyType, List<Omission> omissions, Omission topExpect) {
        List<Omission> returnList = new ArrayList<>();
        Map<String, List<Omission>> oneBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberOneBall));
        for (Map.Entry<String, List<Omission>> entry : oneBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 1, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 1, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> twoBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberTwoBall));
        for (Map.Entry<String, List<Omission>> entry : twoBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 2, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 2, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> threeBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberThreeBall));
        for (Map.Entry<String, List<Omission>> entry : threeBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 3, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 3, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> fourBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberFourBall));
        for (Map.Entry<String, List<Omission>> entry : fourBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 4, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 4, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> fiveBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberFiveBall));
        for (Map.Entry<String, List<Omission>> entry : fiveBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 5, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 5, entry.getKey(), varietyType));
        }
        if (varietyType == 0 || varietyType == 1) {
            Map<String, List<Omission>> sixBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberSixBall));
            for (Map.Entry<String, List<Omission>> entry : sixBallCollect.entrySet()) {
                Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 6, null), entry.getKey(), varietyType, topExpect, entry.getValue());
                returnList.add(packageOmission(omissions1, 6, entry.getKey(), varietyType));
            }
            Map<String, List<Omission>> sevenBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberSevenBall));
            for (Map.Entry<String, List<Omission>> entry : sevenBallCollect.entrySet()) {
                Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 7, null), entry.getKey(), varietyType, topExpect, entry.getValue());
                returnList.add(packageOmission(omissions1, 7, entry.getKey(), varietyType));
            }
            Map<String, List<Omission>> eightBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberEightBall));
            for (Map.Entry<String, List<Omission>> entry : eightBallCollect.entrySet()) {
                Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 8, null), entry.getKey(), varietyType, topExpect, entry.getValue());
                returnList.add(packageOmission(omissions1, 8, entry.getKey(), varietyType));
            }
            Map<String, List<Omission>> nineBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberNineBall));
            for (Map.Entry<String, List<Omission>> entry : nineBallCollect.entrySet()) {
                Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 9, null), entry.getKey(), varietyType, topExpect, entry.getValue());
                returnList.add(packageOmission(omissions1, 9, entry.getKey(), varietyType));
            }
            Map<String, List<Omission>> tenBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberTenBall));
            for (Map.Entry<String, List<Omission>> entry : tenBallCollect.entrySet()) {
                Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 10, null), entry.getKey(), varietyType, topExpect, entry.getValue());
                returnList.add(packageOmission(omissions1, 10, entry.getKey(), varietyType));
            }
        }

        return returnList;
    }
   
    public static Long getOmissions(String ball, String key, int varietyType, Omission topExpect, List<Omission> omissions) {
        Omission secondExpect = omissions.get(0);//开出当前冠亚和的最新一期
        Long count = 0L;
        if (!ball.equals(key)) {
            //如果最新一期不是当前冠亚和，开出当前冠亚和的最新一期减去最新一期的值为当前遗漏
            if (varietyType == 1) {
                count = topExpect.getExpect() - secondExpect.getExpect();
            } else {
                count = Long.valueOf(CalcOmit.subExpect(varietyType, topExpect.getExpect().toString(), secondExpect.getExpect().toString()));
            }
        } else {
            //如果是，返回-1
            count += -1;
            for (int i = 0; i < omissions.size(); i++) {
                if (topExpect.getExpect().toString().equals(omissions.get(i).getExpect().toString())) {
                    continue;
                }
                //判断期数是否是连续开出当前冠亚和
                if (!((i + 1) == omissions.size())) {
                    if (varietyType == 1) {
                        if (topExpect.getExpect() - i == omissions.get(i).getExpect()) {
                            //如果是，继续加-1
                            count += -1;
                        }
                    } else {
                        if (CalcOmit.subExpect(varietyType, topExpect.getExpect().toString(), omissions.get(i).getExpect().toString()) == i) {
                            //如果是，继续加-1
                            count += -1;
                        }
                    }
                }
            }
        }
        return count;
    }

    protected static Omission packageOmission(Long count, int ball, String desc, int varietyType) {
        Omission currentOmission = new Omission();
        /*if (ball == 1) currentOmission.setNumberOneBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 2) currentOmission.setNumberTwoBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 3) currentOmission.setNumberThreeBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 4) currentOmission.setNumberFourBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 5) currentOmission.setNumberFiveBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 6) currentOmission.setNumberSixBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 7) currentOmission.setNumberSevenBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 8) currentOmission.setNumberEightBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 9) currentOmission.setNumberNineBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        if (ball == 10) currentOmission.setNumberTenBall(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);*/
        currentOmission.setSingleDouble(NumberHandlerHelper.getNumberDesc(varietyType, ball) + desc);
        currentOmission.setCurrentOmi(count);
        return currentOmission;
    }

    public static List<Omission> getDayOmiss(int varietyType, List<Omission> omissions, String dateType) {
        List<Omission> returnList = new ArrayList<>();
        List<String> numberOneBall = Lists.transform(omissions, omission -> omission.getNumberOneBall());//获取冠军位单双的集合
        returnList.addAll(PackageSingleDoubleOmission(1, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberOneBall), dateType));

        List<String> numberTwoBall = Lists.transform(omissions, omission -> omission.getNumberTwoBall());//获取亚军位单双的集合
        returnList.addAll(PackageSingleDoubleOmission(2, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberTwoBall), dateType));

        List<String> numberThreeBall = Lists.transform(omissions, omission -> omission.getNumberThreeBall());//获取第三球单双的集合
        returnList.addAll(PackageSingleDoubleOmission(3, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberThreeBall), dateType));

        List<String> numberFourBall = Lists.transform(omissions, omission -> omission.getNumberFourBall());//获取第四球单双的集合
        returnList.addAll(PackageSingleDoubleOmission(4, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberFourBall), dateType));

        List<String> numberFiveBall = Lists.transform(omissions, omission -> omission.getNumberFiveBall());//获取第五球单双的集合
        returnList.addAll(PackageSingleDoubleOmission(5, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberFiveBall), dateType));

        if (varietyType == 0 || varietyType == 1) {
            List<String> numberSixBall = Lists.transform(omissions, omission -> omission.getNumberSixBall());//获取第六球单双的集合
            returnList.addAll(PackageSingleDoubleOmission(6, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberSixBall), dateType));
            List<String> numberSevenBall = Lists.transform(omissions, omission -> omission.getNumberSevenBall());//获取第七球单双的集合
            returnList.addAll(PackageSingleDoubleOmission(7, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberSevenBall), dateType));
            List<String> numberEightBall = Lists.transform(omissions, omission -> omission.getNumberEightBall());//获取第八球单双的集合
            returnList.addAll(PackageSingleDoubleOmission(8, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberEightBall), dateType));
            List<String> numberNineBall = Lists.transform(omissions, omission -> omission.getNumberNineBall());//获取第九球单双的集合
            returnList.addAll(PackageSingleDoubleOmission(9, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberNineBall), dateType));
            List<String> numberTenBall = Lists.transform(omissions, omission -> omission.getNumberTenBall());//获取第十球单双的集合
            returnList.addAll(PackageSingleDoubleOmission(10, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberTenBall), dateType));
        }
        return returnList;
    }

    public static List<Omission> PackageSingleDoubleOmission(Integer number, int varietyType, Map<String, Integer> twoFaceMaxOmission, String dateType) {
        List<Omission> omissionList = new ArrayList<>();
        for (Map.Entry<String, Integer> entries : twoFaceMaxOmission.entrySet()) {
            Omission currentOmission = new Omission();
            currentOmission.setSingleDouble(NumberHandlerHelper.getNumberDesc(varietyType, number) + entries.getKey());
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
    public static List<Omission> getDragonTigerDayOmiss(int varietyType, List<Omission> omissions, String dateType) {
        List<Omission> returnList = new ArrayList<>();
        List<String> numberOneBall = Lists.transform(omissions, omission -> omission.getNumberOneBall());//获取冠军位单双的集合
        returnList.addAll(PackageSingleDoubleOmission(1, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberOneBall), dateType));

        List<String> numberTwoBall = Lists.transform(omissions, omission -> omission.getNumberTwoBall());//获取亚军位单双的集合
        returnList.addAll(PackageSingleDoubleOmission(2, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberTwoBall), dateType));

        List<String> numberThreeBall = Lists.transform(omissions, omission -> omission.getNumberThreeBall());//获取第三球单双的集合
        returnList.addAll(PackageSingleDoubleOmission(3, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberThreeBall), dateType));

        List<String> numberFourBall = Lists.transform(omissions, omission -> omission.getNumberFourBall());//获取第四球单双的集合
        returnList.addAll(PackageSingleDoubleOmission(4, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberFourBall), dateType));

        List<String> numberFiveBall = Lists.transform(omissions, omission -> omission.getNumberFiveBall());//获取第五球单双的集合
        returnList.addAll(PackageSingleDoubleOmission(5, varietyType, PackageOmissionDataHelper.getTwoFaceMaxOmission(numberFiveBall), dateType));
        return returnList;
    }
    /**
     * 计算龙虎的今日遗漏
     *
     * @param varietyType
     * @param omissions
     * @param topExpect
     * @return
     */
    public static List<Omission> getDragonTigerCurrentOmission(int varietyType, List<Omission> omissions, Omission topExpect) {
        List<Omission> returnList = new ArrayList<>();
        Map<String, List<Omission>> oneBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberOneBall));
        for (Map.Entry<String, List<Omission>> entry : oneBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 1, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 1, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> twoBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberTwoBall));
        for (Map.Entry<String, List<Omission>> entry : twoBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 2, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 2, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> threeBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberThreeBall));
        for (Map.Entry<String, List<Omission>> entry : threeBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 3, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 3, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> fourBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberFourBall));
        for (Map.Entry<String, List<Omission>> entry : fourBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 4, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 4, entry.getKey(), varietyType));
        }
        Map<String, List<Omission>> fiveBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberFiveBall));
        for (Map.Entry<String, List<Omission>> entry : fiveBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 5, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageOmission(omissions1, 5, entry.getKey(), varietyType));
        }
        return returnList;
    }
    /**
     * 计算冠亚和今日遗漏
     *
     * @param varietyType
     * @param omissions
     * @param topExpect
     * @return
     */
    public static List<Omission> getCrownSubCurrentOmission(int varietyType, List<Omission> omissions, Omission topExpect) {
        List<Omission> returnList = new ArrayList<>();
        Map<String, List<Omission>> oneBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberOneBall));
        for (Map.Entry<String, List<Omission>> entry : oneBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 1, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageCurrrentOmission(omissions1,  entry.getKey()));
        }
        Map<String, List<Omission>> twoBallCollect = omissions.stream().collect(Collectors.groupingBy(Omission::getNumberTwoBall));
        for (Map.Entry<String, List<Omission>> entry : twoBallCollect.entrySet()) {
            Long omissions1 = getOmissions(NumberHandlerHelper.getFieldValue(topExpect, 2, null), entry.getKey(), varietyType, topExpect, entry.getValue());
            returnList.add(packageCurrrentOmission(omissions1,  entry.getKey()));
        }
        return returnList;
    }
    public static Omission packageCurrrentOmission(Long count, String desc) {
        Omission currentOmission = new Omission();
        currentOmission.setSingleDouble("总和" + desc);
        currentOmission.setCurrentOmi(count);
        return currentOmission;
    }
}
