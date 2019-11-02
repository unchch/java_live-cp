package com.bh.live.model.inform;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by zhoumaofan on 2019-7-1 10:54
 */

@Data
public class Omission implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 期数
     */
    private Long expect;

    /**
     * 开出的号码
     */
    private Integer numberBall;
    /**
     * 开出的号码描述
     */
    private Integer numberDesc;
    /**
     * 冠亚和单双大小描述
     */
    private String crownSubDesc;
    /**
     * 冠亚和大小描述
     */
    private String bigSmall;
    /**
     * 冠亚和单双描述
     */
    private String singleDouble;


    private String numberOneBall;
    private String numberTwoBall;
    private String numberThreeBall;
    private String numberFourBall;
    private String numberFiveBall;
    private String numberSixBall;
    private String numberSevenBall;
    private String numberEightBall;
    private String numberNineBall;
    private String numberTenBall;
    /**
     * 冠亚和
     */
    private Integer crownSub;

    /**
     * 今日出现次数
     */
    private Long todayAppear;
    /**
     * 当前遗漏
     */
    private Long currentOmi;
    /**
     * 今日遗漏
     */
    private Long todayOmi;
    /**
     * 本周遗漏
     */
    private Long thisWeekOmi;
    /**
     * 本月遗漏
     */
    private Long thisMonthOmi;
    /**
     * 历史遗漏
     */
    private Long historyOmi;
    
    private Date openTime;
}
