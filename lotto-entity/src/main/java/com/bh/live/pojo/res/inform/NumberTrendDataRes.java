package com.bh.live.pojo.res.inform;

import lombok.Data;

/**
 * @Description: 开奖码走势图数据
 * @Author: Dingo
 * @Version: 1.0
 * @date: 2019/6/15 14:17
 */
@Data
public class NumberTrendDataRes {
    /**
     * 期号后两位
     */
    private String period;
    /**
     * 数字
     */
    private  String number;
}
