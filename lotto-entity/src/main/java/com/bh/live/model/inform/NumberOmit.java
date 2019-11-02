package com.bh.live.model.inform;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class NumberOmit implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 期数
     */
    private String expect;

    /**
     * 1号
     */
    private Integer numberOneBall;
    /**
     * 2号
     */
    private Integer numberTwoBall;
    /**
     * 3号
     */
    private Integer numberThreeBall;
    /**
     * 4号
     */
    private Integer numberFourBall;
    /**
     * 5号
     */
    private Integer numberFiveBall;
    /**
     * 6号
     */
    private Integer numberSixBall;
    /**
     * 7号
     */
    private Integer numberSevenBall;
    /**
     * 8号
     */
    private Integer numberEightBall;
    /**
     * 9号
     */
    private Integer numberNineBall;
    /**
     * 10号
     */
    private Integer numberTenBall;

    /**
     * 创建时间
     */
    private Date openTime;
    /**
     * 冠军龙虎
     */
    private String firstDragonTiger;
    /**
     * 亚军龙虎
     */
    private String secondDragonTiger;
    /**
     * 第三名龙虎
     */
    private String thirdDragonTiger;
    /**
     * 第四名龙虎
     */
    private String fourthDragonTiger;
    /**
     * 第五名龙虎
     */
    private String fifthDragonTiger;

    /**
     * 冠亚和
     */
    private Integer crownSub;
    /**
     * 冠亚和单双
     */
    private String singleDouble;
    /**
     * 冠亚和大小
     */
    private String bigSmall;


}
