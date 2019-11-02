package com.bh.live.pojo.res.inform;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 开奖结果
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/11 17:36
 */
@Data
@ToString
public class ColorVarietyModelRes implements Serializable {

    /**
     * ID
     */
    private Integer id;
    /**
     * 期号
     */
    private String expect;
    /**
     * 开奖时间
     */
    private String openTime;
    /**
     *   彩种类型
     */
    private String varietyType;
    /**
     * 开奖时间戳
     */
    private String openTimeStamp;
    /**
     * 开奖码字符串
     */
    private String openCode;
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
    private String createTime;
    /**
     * 冠亚和
     */
    private String crownSub;
    /**
     * 单双
     */
    private String singleDouble;
    /**
     * 大小
     */
    private String bigSmall;
    /**
     * 一号龙虎
     */
    private String firstDragonTiger;
    /**
     * 二号龙虎
     */
    private String secondDragonTiger;
    /**
     * 三号龙虎
     */
    private String thirdDragonTiger;
    /**
     * 四号龙虎
     */
    private String fourthDragonTiger;
    /**
     * 五号龙虎
     */
    private String fifthDragonTiger;

    /**
     * 龙虎字符串
     */
    private String additionalStr;


}
