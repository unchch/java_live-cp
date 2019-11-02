package com.bh.live.common.enums.lottery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgs
 * @title: LotteryEnum
 * @projectName java_live-cp
 * @description: 彩种枚举
 * @date 2019/7/30  20:13
 */
public class LotteryEnum {

    /**
     * 彩种枚举
     */
    @AllArgsConstructor
    @Getter
    public enum LottoSeedNoEnum {

        CQ_SSC(101, "重庆时时彩"),
        LUCKY_AIRSHIP(401, "幸运飞艇"),
        BJ_CAR(402, "北京赛车"),
        HONG_KONG(801, "香港彩"),
        ;

        private int code;

        private String name;

        public static LottoSeedNoEnum getLottoSeed(int lotteryCode) {
            for (LottoSeedNoEnum value : LottoSeedNoEnum.values()) {
                if (value.getCode() == lotteryCode) {
                    return value;
                }
            }
            return null;
        }
    }

}

