package com.bh.live.pojo.res.live;


import lombok.Data;

import java.util.Date;


/**
 * @Description: 聊天室开奖号码
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/8 14:03
 */
@Data
public class ChatOpenCode{

    /**
     * 上一期期号
     */
    private String periods;
    /**
     * 上一期彩果
     */
    private String openCode;

    /**
     * 总期数
     */
    private Integer total;

    /**
     * 上一期时剩余期数
     */
    private Integer surplus;

    /**
     * 当前期期号
     */
    private String nextPeriods;

    /**
     *  当前期倒计时
     */
    private long nextTime;
}
