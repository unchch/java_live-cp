package com.bh.live.common.constant;

/**
 *@Author: WuLong
 *@Date: 2019/7/15 21:00
 *@Description: 消息队列名常量类
 */
public class QueueConstants {
    /**
     * 事例消息队列名
     */
    public static final String TEST_QUEUE = "TEST_QUEUE";

    /**
     * 开奖消息队列
     */
    public static final String LOTTERY_RESULT_QUEUE = "lottery_result";

    /**
     * 开奖后通知交易中心，增加购买推荐账户的流水到方案发布者账户
     */
    public static final String LOTTERY_TRANS_QUEUE = "lottery_trans";


}
