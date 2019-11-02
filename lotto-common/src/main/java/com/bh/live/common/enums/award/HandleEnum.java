
package com.bh.live.common.enums.award;

import com.bh.live.common.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuLong
 * @date 2019/8/8 22:31
 * @description 开奖枚举
 */
public class HandleEnum {

    @AllArgsConstructor
    @Getter
    public enum AwardTypeEnum {

        OPEN_AWARD(1, "开奖"),

        REST_OPEN_AWARD(2, "重置开奖");

        private Integer code;

        private String desc;

        public static AwardTypeEnum getEnumByCode(Integer code) {
            if (null == code) {
                return null;
            }
            for (AwardTypeEnum typeEnum : AwardTypeEnum.values()) {
                if (typeEnum.code.equals(code)) {
                    return typeEnum;
                }
            }
            return null;
        }
    }

    /**
     * @author WuLong
     * @date 2019/8/9 0:09
     * @description 彩种类型
     */
    @AllArgsConstructor
    @Getter
    public enum LotteryTypeEnum {
        NUMBER(1, "数字"),
        SPORTS(2, "竞技");

        private Integer code;

        private String desc;

        public static LotteryTypeEnum getEnumByCode(Integer code) {
            if (null == code) {
                return null;
            }
            for (LotteryTypeEnum lotteryTypeEnum : LotteryTypeEnum.values()) {
                if (lotteryTypeEnum.code.equals(code)) {
                    return lotteryTypeEnum;
                }
            }
            return null;
        }
    }

    /**
     * @author WuLong
     * @date 2019/8/12 0:09
     * @description 虚拟类bit枚举，北京赛车，幸运飞艇
     */
    @AllArgsConstructor
    @Getter
    public enum FictitiousBitEnum {
        NUMBER("号码", "号码"),
        TWO_SIDE("两面", "两面"),
        GUAN_YA_HE("冠亚总和","冠亚总和");

        private String code;

        private String desc;

        public static FictitiousBitEnum getEnumByCode(String code) {
            if (CommonUtil.isEmpty(code)) {
                return null;
            }
            for (FictitiousBitEnum fictitiousBitEnum : FictitiousBitEnum.values()) {
                if (fictitiousBitEnum.code.equals(code)) {
                    return fictitiousBitEnum;
                }
            }
            return null;
        }
    }

    /**
     * @author WuLong
     * @date 2019/8/12 0:09
     * @description 虚拟类item枚举，北京赛车，幸运飞艇
     */
    @AllArgsConstructor
    @Getter
    public enum FictitiousItemEnum {
        BIG("大", "大"),
        SMALL("小", "小"),
        SINGLE("单", "单"),
        DUAL("双", "双"),
        DRAGON("龙", "龙"),
        TIGER("虎", "虎"),
        HE("和","和"),
        ;

        private String code;

        private String desc;

        public static FictitiousItemEnum getEnumByCode(String code) {
            if (CommonUtil.isEmpty(code)) {
                return null;
            }
            for (FictitiousItemEnum fictitiousItemEnum : FictitiousItemEnum.values()) {
                if (fictitiousItemEnum.code.equals(code)) {
                    return fictitiousItemEnum;
                }
            }
            return null;
        }
    }

    /**
     * @author WuLong
     * @date 2019/8/12 0:09
     * @description 订单开奖枚举，注单派奖状态 - 0:自动，1:手动，2:重置
     */
    @AllArgsConstructor
    @Getter
    public enum PrizeStatusEnum {
        AUTOMATIC(0, "自动"),
        MANUAL(1, "手动"),
        RESET(2, "重置");

        private Integer code;

        private String desc;

        public static PrizeStatusEnum getEnumByCode(Integer code) {
            if (CommonUtil.isEmpty(code)) {
                return null;
            }
            for (PrizeStatusEnum prizeStatusEnum : PrizeStatusEnum.values()) {
                if (prizeStatusEnum.code.equals(code)) {
                    return prizeStatusEnum;
                }
            }
            return null;
        }
    }

}
