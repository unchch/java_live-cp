//package com.bh.live.common.utils.high;
//
//import com.bh.live.common.enums.LotteryChildEnum;
//
//import java.util.EnumSet;
//import java.util.Objects;
//
///**
// * @author lgs
// * @title: HighUtil
// * @projectName java_live-cp
// * @description: 高频彩工具类
// * @date 2019/7/19  14:05
// */
//public class HighUtil {
//
//    /**
//     * 获取幸运飞艇子彩种
//     *
//     * @return
//     */
//    private static EnumSet<LotteryChildEnum> getLuckyAirshipChild() {
//        EnumSet<LotteryChildEnum> lotteryChildSet = EnumSet.of(
//                LotteryChildEnum.LUCKY_AIRSHIP_CHAMPION,
//                LotteryChildEnum.LUCKY_AIRSHIP_RUNNER_UP,
//                LotteryChildEnum.LUCKY_AIRSHIP_THIRD,
//                LotteryChildEnum.LUCKY_AIRSHIP_FOURTH_PLACE,
//                LotteryChildEnum.LUCKY_AIRSHIP_FIFTH_PLACE,
//                LotteryChildEnum.LUCKY_AIRSHIP_SIXTH_PLACE,
//                LotteryChildEnum.LUCKY_AIRSHIP_SEVENTH_PLACE,
//                LotteryChildEnum.LUCKY_AIRSHIP_EIGHTH_PLACE,
//                LotteryChildEnum.LUCKY_AIRSHIP_NINTH_PLACE,
//                LotteryChildEnum.LUCKY_AIRSHIP_TENTH_PLACE,
//                LotteryChildEnum.LUCKY_AIRSHIP_DT,
//                LotteryChildEnum.LUCKY_AIRSHIP_SIZE,
//                LotteryChildEnum.LUCKY_AIRSHIP_SD
//        );
//        return lotteryChildSet;
//    }
//
//    /**
//     * 获取北京赛车子彩种
//     *
//     * @return
//     */
//    private static EnumSet<LotteryChildEnum> getBjCarChild() {
//        EnumSet<LotteryChildEnum> lotteryChildSet = EnumSet.of(
//                LotteryChildEnum.BJ_CAR_CHAMPION,
//                LotteryChildEnum.BJ_CAR_RUNNER_UP,
//                LotteryChildEnum.BJ_CAR_THIRD,
//                LotteryChildEnum.BJ_CAR_FOURTH_PLACE,
//                LotteryChildEnum.BJ_CAR_FIFTH_PLACE,
//                LotteryChildEnum.BJ_CAR_SIXTH_PLACE,
//                LotteryChildEnum.BJ_CAR_SEVENTH_PLACE,
//                LotteryChildEnum.BJ_CAR_EIGHTH_PLACE,
//                LotteryChildEnum.BJ_CAR_NINTH_PLACE,
//                LotteryChildEnum.BJ_CAR_TENTH_PLACE,
//                LotteryChildEnum.BJ_CAR_DT,
//                LotteryChildEnum.BJ_CAR_SIZE,
//                LotteryChildEnum.BJ_CAR_SD
//        );
//        return lotteryChildSet;
//    }
//
//
//    /**
//     * 是否是幸运飞艇玩法
//     *
//     * @param childCode
//     * @return
//     */
//    public static boolean isLuckyAirship(Integer childCode) {
//        return isLotteryChild(getLuckyAirshipChild(), childCode);
//    }
//
//    /**
//     * 是否是北京赛车玩法
//     *
//     * @param childCode
//     * @return
//     */
//    public static boolean isBjCar(Integer childCode) {
//        return isLotteryChild(getBjCarChild(), childCode);
//    }
//
//
//    /**
//     * 判断这个子彩种是否属于目标子彩种
//     *
//     * @param lotteryChildSet
//     * @param lotteryChildCode
//     * @return
//     */
//    public static boolean isLotteryChild(EnumSet<LotteryChildEnum> lotteryChildSet, Integer lotteryChildCode) {
//        if (lotteryChildSet == null || lotteryChildCode == null) {
//            return false;
//        }
//        for (LotteryChildEnum child : lotteryChildSet) {
//            if (Objects.equals(child.getCode(), lotteryChildCode)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//}
