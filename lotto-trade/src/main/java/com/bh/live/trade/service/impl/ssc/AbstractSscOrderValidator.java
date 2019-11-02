package com.bh.live.trade.service.impl.ssc;

import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.Play;
import com.bh.live.pojo.req.order.OrderDetailReq;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.trade.service.AbstractOrderDetailValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lgs
 * @title: AbstractSscOrderValidator
 * @projectName java_live-cp
 * @description: 时时彩抽象类
 * @date 2019/8/5  10:17
 */
@Slf4j
public abstract class AbstractSscOrderValidator extends AbstractOrderDetailValidator {

    @Override
    public Result<?> handleProcess(OrderReq req) {
        Result<?> result = handle(req);
        if (result.getCode() != 200) {
            throw new ResultJsonException(result);
        }
        return result;
    }

    /**
     * 解析投注内容
     *
     * @return
     */
    public int analysisContent(OrderDetailReq req, Play play) {
        int betNum = 0;
        String content = req.getContent();
//        switch (LotteryPlayEnum.getLotteryPlay(play.getPlayNo())) {
//            default:
//                throw new ResultJsonException(CodeMsg.E_50022);
//        }
        return betNum;
    }

}
