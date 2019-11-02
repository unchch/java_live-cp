package com.bh.live.pojo.res.live;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description: 竞猜
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/8 14:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGuess {

    /**
     * 期号
     */
    private String periods;

    /**
     *  订单ID
     */
    private Integer orderId;

    /**
     * 内容
     */
    private String content;

    /**
     * 输赢状态
     */
    private String awardState;
    /**
     * 彩币
     */
    private BigDecimal amount;

}
