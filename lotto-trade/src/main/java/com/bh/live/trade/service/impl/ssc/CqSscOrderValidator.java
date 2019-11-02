package com.bh.live.trade.service.impl.ssc;

import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.order.OrderDetailReq;
import com.bh.live.pojo.req.order.OrderReq;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lgs
 * @title: SScOrderValidator
 * @projectName java_live-cp
 * @description: 重庆时时彩验证
 * @date 2019/8/5  10:16
 */
@Component
public class CqSscOrderValidator extends AbstractSscOrderValidator {

    @Override
    public Result<int[]> validatorContent(OrderReq req) {
        List<OrderDetailReq> detailReqs = req.getContent();
        final int[] betNum = {0};
        detailReqs.forEach(v -> {
//            if (v.getPlayNo().equals(k.getPlayNo())) {
//                int num = analysisContent(v, k);
            v.setBetNum(CommonConstants.ONE);
            betNum[0] += 1;
//            }
        });
        return Result.success(betNum);
    }
}
