package com.bh.live.common.enums.lottery;

import com.bh.live.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgs
 * @title: IssueEnum
 * @projectName java_live-cp
 * @description: 彩期相关枚举
 * @date 2019/7/29  14:52
 */
public class IssueEnum {

    /**
     * 彩种状态枚举
     */
    @AllArgsConstructor
    @Getter
    public enum IssueStatusEnum  implements BaseEnum {
        PRE_SALE(0,"预售中"),
        SALE(1,"销售中"),
        WAITING_AWARD(2,"封盘中等待开奖"),
        OPENING_AWARD(3,"开奖中"),
        AWARD(4,"已开奖等待派奖"),
        SEND_AWARD(5,"已派奖"),
        ;
        private  int code;

        private  String desc;

        public static IssueStatusEnum getIssueStatus(int code){
            for (IssueStatusEnum value : IssueStatusEnum.values()){
                if (value.code == code){
                    return value;
                }
            }
            return null;
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
     * 彩种销售状态枚举
     */
    @AllArgsConstructor
    @Getter
    public enum IssueSaleStateEnum {
        WAIT_SALE(0,"待开售"),
        SALE(1,"销售中"),
        SEALING(2,"封盘中"),
        DEADLINE(3,"已截止"),
        STOP_SALE(4,"已停售"),
        ;
        private  int code;

        private  String desc;
    }

}
