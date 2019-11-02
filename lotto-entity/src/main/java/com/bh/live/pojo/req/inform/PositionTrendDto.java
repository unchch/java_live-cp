package com.bh.live.pojo.req.inform;

import lombok.Data;

/**
 * @Description: 位置走势
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/17 14:22
 */
@Data
public class PositionTrendDto {
    /**
     * 大
     */
    private int big;
    /**
     * 小
     */
    private int small;

    /**
     * 基数：单
     */
    private int odd;

    /**
     * 偶数：双
     */
    private int even;

    /**
     * 期号
     */
    private String period;
    /**
     * 结果
     */
    private int result;
    /**
     * 正向
     */
    private int zx;
    /**
     * 反向
     */
    private int fx;
    /**
     * 重号
     */
    private int ch;

    /**
     * 开奖码
     */
    private String[] nums;

    public PositionTrendDto(String period,String numbers,int fx,int zx,int ch){
        this(period,numbers);
        this.fx=fx;
        this.zx=zx;
        this.ch=ch;
    }

    public PositionTrendDto(){}

    public PositionTrendDto(String period,String numbers){
        this.period=period;
        this.nums=numbers.split(",");
        this.result=Integer.parseInt(nums[0]);
    }

}
