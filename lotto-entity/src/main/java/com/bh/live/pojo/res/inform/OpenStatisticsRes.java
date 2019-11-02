package com.bh.live.pojo.res.inform;

import lombok.Data;

/**
 * @Description: 开奖号码封装类
 * @Author: Dingo
 * @Version: 1.0
 * @date: 2019/6/12 14:22
 */
@Data
public class OpenStatisticsRes {
    /**
     *  类型
     */
    private  Integer variety;
    /**
     *  号码
     */
    private  Integer number;
    /**
     * 字体颜色
     */
    private  String color;

    public OpenStatisticsRes(Integer number) {
        this.number=number;
        this.setNumber(number);
    }

    public OpenStatisticsRes(Integer number, Integer variety) {
        this(number);
        this.setVariety(variety);
    }

    /**
     *  加粗
     */
    private String bold;
    /**
     * 底色
     */
    private String bgcolor;
    /**
     * 单双
     */
    private String singleDouble;
    /**
     * 大小
     */
    private String bigSmall;

    /**
     * 根据数字判断单双大小
     * @param number
     */
    public  void setNumber(Integer number){
        if(number % 2 == 0) {
            this.singleDouble="双";
        }else{
            this.singleDouble="单";
        }
        if(number > 5) {
            this.bigSmall="大";
        }else{
            this.bigSmall="小";
        }
    }
    public  void setVariety(Integer variety){
        int num=5;
        if (variety==2){
            num=4;
        }
        if(number > num) {
            this.bigSmall="大";
        }else{
            this.bigSmall="小";
        }
    }

}
