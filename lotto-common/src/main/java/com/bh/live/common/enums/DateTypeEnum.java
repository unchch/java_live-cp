package com.bh.live.common.enums;

/**
 * @author lgs
 * @title: DateTypeEnum
 * @projectName java_live-cp
 * @description: 日期枚举
 * @date 2019/8/2  11:15
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DateTypeEnum implements BaseEnum{
    LONG_DATE(0,"长期"),
    ONE_DAY(1,"当天"),
    TWO_DAY(2,"2天"),
    THREE_DAY(3,"3天"),
    FOUR_DAY(4,"4天"),
    FIVE_DAY(5,"5天"),
    SIX_DAY(6,"6天"),
    ONE_WEEK(7,"一周"),
    TWO_WEEK(14,"二周"),
    THREE_WEEK(21,"三周"),
    ONE_MOON(30,"一月"),
    TWO_MOON(60,"二月"),
    ;
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    @Override
    public Object code() {
        return code;
    }

    @Override
    public String value() {
        return name;
    }
}
