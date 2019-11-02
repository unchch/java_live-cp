package com.bh.live.pojo.res.inform;

import lombok.Data;

/**
 * @Description: 价值投资
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/19 21:43
 */
@Data
public class ValueInvestRep {
    /**
     * 位置
     */
    private String location;
    /**
     * 玩法
     */
    private String play;
    /**
     * 方案
     */
    private String project;
    /**
     * 出现-累计
     */
    private String appearCount;
    /**
     * 出现-理论
     */
    private double appearTheory;
    /**
     * 出现-偏差
     */
    private double appearDeviation;
    /**
     * 遗漏-当前
     */
    private int omitCurrent;
    /**
     * 遗漏-理论
     */
    private double omitTheory;
    /**
     * 遗漏-平均
     */
    private double  omitAvg;
    /**
     * 遗漏-最大
     */
    private int omitMax;
    /**
     * 指数-欲出
     */
    private String exponentToShow;
    /**
     * 指数-风险
     */
    private String exponentRisk;
    /**
     * 投资价值
     */
    private String valueInvest;


    public ValueInvestRep(String location, String play, String project, String appearCount, double appearTheory, int omitCurrent, double omitTheory, int omitMax) {
        this.location = location;
        this.play = play;
        this.project = project;
        this.appearCount = appearCount;
        this.appearTheory = appearTheory;
        this.omitCurrent=omitCurrent;
        this.omitMax=omitMax;
        this.omitTheory=omitTheory;

    }
}
