package com.bh.live.common.enums.trade;

import com.bh.live.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author lgs
 * @title: OrderEnum
 * @projectName java_live-cp
 * @description: 订单内容相关枚举
 * @date 2019/7/27  14:53
 */
public class OrderEnum {

    /**
     * 订单状态枚举
     */
    @AllArgsConstructor
    @Getter
    public enum OrderStatusEnum {
        TO_BE_PAID(1,"待支付"),
        PENDING_AWARD(2,"待开奖"),
        NOT_WINNING(3,"未中奖"),
        WINNING(4,"已中奖"),
        HAVE_BEEN_AWARDED(5,"已派奖"),
        REST_AWARD(6,"重置开奖"),
        WITHDRAWAL(7,"已撤单"),
        ;
        private Integer code;
        private String desc;

        public static OrderStatusEnum get(int code) {
            return Arrays.asList(values()).stream()
                    .filter(item -> item.getCode() == code)
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * 中奖输赢状态
     */
    @AllArgsConstructor
    @Getter
    public enum AwardStateEnum  implements BaseEnum {
        WIN(1,"赢"),
        DRAW(3,"和"),
        LOST(2,"输"),
        ;
        private Integer code;
        private String desc;

        public static AwardStateEnum get(int code) {
            return Arrays.asList(values()).stream()
                    .filter(item -> item.getCode() == code)
                    .findFirst()
                    .orElse(null);
        }
        @Override
        public Object code() {
            return code;
        }

        @Override
        public String value() {
            return desc;
        }
    }


    /**
     * 免费收费枚举
     */
    @AllArgsConstructor
    @Getter
    public enum IsPayEnum {
        FREE(0,"免费"),
        NO_FREE(1,"收费"),
        ;
        private Integer code;
        private String desc;
    }

}
