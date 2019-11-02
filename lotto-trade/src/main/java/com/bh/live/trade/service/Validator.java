package com.bh.live.trade.service;

import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.common.result.Result;

import java.util.Map;

/**
 * @author lgs
 * @title: Validator
 * @projectName java_live-cp
 * @description: 各大彩种必须实现该接口
 * @date 2019/7/17  13:53
 */
public interface Validator {

    /**
     * 彩种具体处理
     *
     * @param orderReq 订单详情
     * @return
     * @author lgs
     */
    Result<Map<String,Object>> handle(OrderReq orderReq);

}
