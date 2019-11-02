package com.bh.live.pojo.res.inform;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 北京赛车历史统计(单双大小)
 *
 * @author Administrator
 * @title: StatHistoryStatisticsRes
 * @projectName live
 * @description: TODO
 * @date 2019/6/12  14:57
 */
@Getter
@Setter
public class StatHistoryStatisticsRes implements Serializable {

    /**
     * 开奖时间
     */
    private String openTime;
    /**
     * 冠军小
     */
    private Integer oneLittle;
    /**
     * 冠军大
     */
    private Integer oneBig;
    /**
     * 冠军单
     */
    private Integer oneSingle;
    /**
     * 冠军双
     */
    private Integer oneDoubles;

    /**
     * 亚军小
     */
    private Integer twoLittle;
    /**
     * 亚军大
     */
    private Integer twoBig;
    /**
     * 亚军单
     */
    private Integer twoSingle;
    /**
     * 亚军双
     */
    private Integer twoDoubles;

    /**
     * 三小
     */
    private Integer threeLittle;
    /**
     * 三大
     */
    private Integer threeBig;
    /**
     * 三单
     */
    private Integer threeSingle;
    /**
     * 三双
     */
    private Integer threeDoubles;

    /**
     * 四小
     */
    private Integer fourLittle;
    /**
     * 四大
     */
    private Integer fourBig;
    /**
     * 四单
     */
    private Integer fourSingle;
    /**
     * 四双
     */
    private Integer fourDoubles;

    /**
     * 冠军小
     */
    private Integer fiveLittle;
    /**
     * 五大
     */
    private Integer fiveBig;
    /**
     * 五单
     */
    private Integer fiveSingle;
    /**
     * 五双
     */
    private Integer fiveDoubles;

    /**
     * 六小
     */
    private Integer sixLittle;
    /**
     * 六大
     */
    private Integer sixBig;
    /**
     * 六单
     */
    private Integer sixSingle;
    /**
     * 六双
     */
    private Integer sixDoubles;

    /**
     * 七小
     */
    private Integer sevenLittle;
    /**
     * 七大
     */
    private Integer sevenBig;
    /**
     * 七单
     */
    private Integer sevenSingle;
    /**
     * 七双
     */
    private Integer sevenDoubles;

    /**
     * 八小
     */
    private Integer eightLittle;
    /**
     * 八大
     */
    private Integer eightBig;
    /**
     * 八单
     */
    private Integer eightSingle;
    /**
     * 八双
     */
    private Integer eightDoubles;

    /**
     * 九小
     */
    private Integer nineLittle;
    /**
     * 九大
     */
    private Integer nineBig;
    /**
     * 九单
     */
    private Integer nineSingle;
    /**
     * 九双
     */
    private Integer nineDoubles;

    /**
     * 十小
     */
    private Integer tenLittle;
    /**
     * 十大
     */
    private Integer tenBig;
    /**
     * 十单
     */
    private Integer tenSingle;
    /**
     * 十双
     */
    private Integer tenDoubles;
}
