package com.bh.live.pojo.res.inform;

import lombok.Data;

/**
 * @Description: 开奖号码封装类
 * @Author: Dingo
 * @Version: 1.0
 * @date: 2019/6/12 14:22
 */
@Data
public class OpenStatisticsCodeRes {
    /**
     * A期号
     */
    private  String aexpect;
    /**
     * A开奖码字符串
     */
    private  String aopencode;
    /**
     * B期号
     */
    private  String bexpect;
    /**
     * B开奖码字符串
     */
    private  String bopencode;
}
