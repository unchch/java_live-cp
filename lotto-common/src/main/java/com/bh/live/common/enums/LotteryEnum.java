package com.bh.live.common.enums;

/**
 * @author lgs
 * @title: LotteryEnum
 * @projectName java_live-cp
 * @description: 彩种枚举类
 * @date 2019/7/16  15:45
 */
public enum LotteryEnum {

    /*高频彩*/
    /**
     * 北京赛车
     */
    BJ_CAR(2001,"北京赛车"),
    LUCKY_AIRSHIP(2002,"幸运飞艇"),

    /*低频彩*/
    HONEGKONG_LOTTERY(3001,"香港彩票"),
    ;

    /**
     * 彩种编号
     */
    private Integer code;
    /**
     * 彩种名称
     */
    private String name;

    LotteryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
