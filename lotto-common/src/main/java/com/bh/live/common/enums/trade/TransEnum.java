package com.bh.live.common.enums.trade;

import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.pojo.req.trade.TradeTransUserReq;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgs
 * @title: TransEnum
 * @projectName java_live-cp
 * @description: 交易枚举
 * @date 2019/7/27  14:53
 */
public class TransEnum {


    /**
     * 设置收入支出
     *
     * @param tradeTransUser
     */
    public static void setInOut(TradeTransUserReq tradeTransUser) {
        switch (TransEnum.TransTypeEnum.valueOf(tradeTransUser.getTransType())) {
            case RECHARGE:
            case RETURN_PRIZE:
            case SYSTEM_PRESENTATION:
                tradeTransUser.setInOut(TransEnum.IncomeExpenditureEnum.EXPENDITURE.getCode());
                break;
            case REWARD:
            case REFUND_MONEY:
            case DRAWING:
            case BUY:
            case SYSTEM_DEDUCTION:
                tradeTransUser.setInOut(TransEnum.IncomeExpenditureEnum.INCOME.getCode());
                break;
        }
    }

    /**
     * 设置收入支出
     *
     * @param transTypeEnum
     */
    public static TransEnum.IncomeExpenditureEnum getInOut(TransEnum.TransTypeEnum transTypeEnum) {
        switch (transTypeEnum) {
            case RECHARGE:
            case RETURN_PRIZE:
            case SYSTEM_PRESENTATION:
            case ANCHOR_COMMISSION:
                return TransEnum.IncomeExpenditureEnum.EXPENDITURE;
            case REWARD:
            case REFUND_MONEY:
            case DRAWING:
            case BUY:
            case SYSTEM_DEDUCTION:
                return TransEnum.IncomeExpenditureEnum.INCOME;
        }
        return null;
    }

    /**
     * 交易类别枚举
     */
    @AllArgsConstructor
    public enum TransTypeEnum {
        RECHARGE(1, "充值"),
        REWARD(2, "打赏"),
        RETURN_PRIZE(3, "返奖"),
        REFUND_MONEY(4, "退款"),
        DRAWING(5, "提款"),
        BUY(6, "购买推荐"),
        SYSTEM_PRESENTATION(7, "系统赠送"),
        SYSTEM_DEDUCTION(8, "系统扣除"),
        ANCHOR_COMMISSION(9, "主播提成"),
        ;
        private Integer code;
        private String desc;

        public static TransTypeEnum valueOf(int code) {
            for (TransTypeEnum value : TransTypeEnum.values()) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
            throw new ResultJsonException(CodeMsg.E_50103);
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


    /**
     * 交易状态枚举
     */
    @AllArgsConstructor
    @Getter
    public enum TransStatusEnum {
        TRANS_ERROR(0, "交易失败"),
        TRANS_SUCCESS(1, "交易成功"),
        TO_BE_AUDITED(2, "待审核"),
        AUDIT_AND_APPROVAL(3, "审核通过"),
        AUDIT_FAILED(4, "审核不通过"),
        ;
        private Integer code;
        private String desc;
    }

    /**
     * 收入支出枚举
     */
    @AllArgsConstructor
    @Getter
    public enum IncomeExpenditureEnum {
        INCOME(1, "支出"),
        EXPENDITURE(2, "收入"),
        ;
        private Integer code;
        private String desc;

        public static IncomeExpenditureEnum valueOf(int code) {
            for (IncomeExpenditureEnum value : values()) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
            throw new ResultJsonException(CodeMsg.E_50103);
        }
    }
}
