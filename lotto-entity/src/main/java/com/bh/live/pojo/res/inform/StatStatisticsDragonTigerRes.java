package com.bh.live.pojo.res.inform;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 北京赛车历史统计龙虎返回VO
 *
 * @author Administrator
 * @title: StatStatisticsDragonTigerRes
 * @projectName live
 * @description: TODO
 * @date 2019/6/13  11:18
 */
@Setter
@Getter
@ToString
public class StatStatisticsDragonTigerRes implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 开奖时间
     */
    private String openTime;
    /**
     * 冠军龙次数
     */
    private Integer championDragon;
    /**
     * 冠军虎次数
     */
    private Integer championTiger;
    /**
     * 亚军龙次数
     */
    private Integer secondDragon;
    /**
     * 亚军虎次数
     */
    private Integer secondTiger;
    /**
     * 第三龙次数
     */
    private Integer thirdDragon;
    /**
     * 第三虎次数
     */
    private Integer thirdTiger;
    /**
     * 第四龙次数
     */
    private Integer fourthDragon;
    /**
     * 第四虎次数
     */
    private Integer fourthTiger;
    /**
     * 第五龙次数
     */
    private Integer fifthDragon;
    /**
     * 第五虎次数
     */
    private Integer fifthTiger;
}
