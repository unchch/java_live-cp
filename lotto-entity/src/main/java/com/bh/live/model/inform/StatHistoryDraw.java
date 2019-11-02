package com.bh.live.model.inform;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
public class StatHistoryDraw implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开奖期数
     */
    private String expect;

    /**
     * 开奖时间
     */
    private Date openTime;

    /**
     * 开奖吗
     */
    private String openCode;

    /**
     * 1号
     */
    private String numberOneBall;
    /**
     * 2号
     */
    private String numberTwoBall;
    /**
     * 3号
     */
    private String numberThreeBall;
    /**
     * 4号
     */
    private String numberFourBall;
    /**
     * 5号
     */
    private String numberFiveBall;
    /**
     * 6号
     */
    private String numberSixBall;
    /**
     * 7号
     */
    private String numberSevenBall;
    /**
     * 8号
     */
    private String numberEightBall;
    /**
     * 9号
     */
    private String numberNineBall;
    /**
     * 10号
     */
    private String numberTenBall;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 冠亚和
     */
    private Integer crownSub;

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
     * 前三
     */
    private String topThree;

    /**
     * 中三
     */
    private String middleThree;

    /**
     * 后三
     */
    private String afterThree;

    /**
     * 上/下/中
     */
    private String upAndDown;

    /**
     * 五行
     */
    private String fiveElements;

    /**
     * 快樂8  飛盤號
     */
    private Integer reservedValue;

    /**
     * 幸運農場 总和(尾大/尾小)
     */
    private String tailBigSmall;

    /**
     * 香港彩 特码单双
     */
    private String specialCodeSingleDouble;

    /**
     * 香港彩 特码 大小
     */
    private String specialCodeBigSmall;

    public String getTailBigSmall() {
        if(this.crownSub != null){
            if(Integer.parseInt(this.crownSub.toString().substring(this.crownSub.toString().length()-1)) >=5){
                this.tailBigSmall ="尾大";
            }else {
                this.tailBigSmall ="尾小";
            }
        }
        return tailBigSmall;
    }

    /**
     *
     */
   // @Transient
    public String strDate;

    public String getStrDate() {
        if(getExpect().length() > 10){
            return getExpect().substring(0, 8) + "-" + getExpect().substring(8, 11);
        }
        return this.getExpect();
    }
    
    private String zodiac; //生肖
}
