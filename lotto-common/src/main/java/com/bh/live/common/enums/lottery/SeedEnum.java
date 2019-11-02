package com.bh.live.common.enums.lottery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgs
 * @title: SeedEnum
 * @projectName java_live-cp
 * @description: 彩种枚举
 * @date 2019/7/29  14:41
 */
public class SeedEnum {

    /**
     * 彩种销售状态枚举
     */
    @AllArgsConstructor
    @Getter
    public enum SaleStateEnum {
        WAIT_SALE(0,"待开售"),
        SALE(1,"销售中"),
        SEALING(2,"封盘中"),
        DEADLINE(3,"已截止"),
        STOP_SALE(4,"已停售"),
        ;
        private  int code;

        private  String desc;
    }

    /**
     * 彩种状态枚举
     */
    @AllArgsConstructor
    @Getter
    public enum StatusEnum {
        NO_MAINTENANCE_STATUS(0,"非维护状态"),
        MAINTENANCE_STATUS(1,"维护状态"),
        ;
        private  int code;

        private  String desc;
    }


}
