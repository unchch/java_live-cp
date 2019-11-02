package com.bh.live.pojo.res.inform;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 * @title: StatStatisticsCrownCountRes
 * @projectName live
 * @description: TODO
 * @date 2019/6/13  9:50
 */
@Data
public class StatStatisticsCrownCountRes implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 开奖时间
     */
    private String openTime;
    /**
     * 冠亚和大次数
     */
    private Integer crownSubBig;
    /**
     * 冠亚和小次数
     */
    private Integer crownSubLittle;
    /**
     * 冠亚和单次数
     */
    private Integer oddNumber;
    /**
     * 冠亚和双次数
     */
    private Integer evenNumber;
}
