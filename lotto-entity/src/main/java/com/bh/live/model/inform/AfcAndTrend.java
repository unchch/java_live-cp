package com.bh.live.model.inform;

import lombok.Data;

import java.io.Serializable;

/**
 * 亚冠和走势
 */
@Data
public class AfcAndTrend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 亚冠和等于 3 的次数
     */
    private Integer threeCount;

    /**
     * 亚冠和等于 4 的次数
     */
    private Integer fourCount;

    /**
     * 亚冠和等于 5 的次数
     */
    private Integer fiveCount;

    /**
     * 亚冠和等于 6 的次数
     */
    private Integer sixCount;

    /**
     * 亚冠和等于 7 的次数
     */
    private Integer sevenCount;

    /**
     * 亚冠和等于 8 的次数
     */
    private Integer eightCount;

    /**
     * 亚冠和等于 9 的次数
     */
    private Integer nineCount;

    /**
     * 亚冠和等于 10 的次数
     */
    private Integer tenCount;

    /**
     * 亚冠和等于 11 的次数
     */
    private Integer elevenCount;

    /**
     * 亚冠和等于 12 的次数
     */
    private Integer twelveCount;

    /**
     * 亚冠和等于 13 的次数
     */
    private Integer thirteenCount;

    /**
     * 亚冠和等于 14 的次数
     */
    private Integer fourteenCount;

    /**
     * 亚冠和等于 15 的次数
     */
    private Integer fifteenCount;

    /**
     * 亚冠和等于 16 的次数
     */
    private Integer sixteenCount;

    /**
     * 亚冠和等于 17 的次数
     */
    private Integer seventeenCount;

    /**
     * 亚冠和等于 18 的次数
     */
    private Integer eighteenCount;

    /**
     * 亚冠和等于 19 的次数
     */
    private Integer nineteenCount;

    /**
     * 亚冠和单 次数
     */
    private Integer singleCount;

    /**
     * 亚冠和双 次数
     */
    private Integer doubleCount;

    /**
     * 亚冠和大 次数
     */
    private Integer bigCount;

    /**
     * 亚冠和小 次数
     */
    private Integer smallCount;

    /**
     * 亚冠和 前 次数
     */
    private Integer beforeCount;

    /**
     * 亚冠和 中 次数
     */
    private Integer middleCount;

    /**
     * 亚冠和 后 次数
     * @return
     */
    private Integer behindCount;

    /**
     *  回摆 ----> 反向
     */
    private Integer reverseCount;

    /**
     *  回摆 ----> 重号
     */
    private Integer heavyCount;

    /**
     *  回摆 ----> 正向
     */
    private Integer positiveCount;
}
