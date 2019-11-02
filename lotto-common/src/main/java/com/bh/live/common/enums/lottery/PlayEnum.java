package com.bh.live.common.enums.lottery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgs
 * @title: PlayEnum
 * @projectName java_live-cp
 * @description: 玩法枚举
 * @date 2019/7/29  16:35
 */
public class PlayEnum {





    @AllArgsConstructor
    @Getter
    public enum PlayStatusEnum{
        STOP(0,"停用"),
        ON(1,"启用"),
        ;
        private  int code;

        private  String desc;
    }

}
