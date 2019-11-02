package com.bh.live.pojo.res.inform;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 号码遗漏和冠亚和遗漏通用查询返回对象VO
 *
 * @author yyf
 * @title: NumberMissingVo
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/25  15:48
 */
@Getter
@Setter
@ToString
public class NumberMissingVo {
    /**
     * 号码组合
     */
    private String numberCombination;
    /**
     * 今日出现次数
     */
    private Integer todayAppear;
    /**
     * 今日遗漏次数
     */
    private Integer todayMissing;
    /**
     * 当前遗漏次数
     */
    private Integer currentFrequency;
    /**
     * 本周遗漏次数
     */
    private Integer week;
    /**
     * 本月遗漏次数
     */
    private Integer month;
    /**
     * 历史遗漏
     */
    private Integer history;
    /**
     * 号码(第一球/第二球...)
     */
    private String rankName;
    /**
     * 选号规则
     */
    private String chooseRules;
    /**
     * 彩种类型
     */
    private String varietyType;
}
