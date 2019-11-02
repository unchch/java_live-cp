package com.bh.live.trade.service.impl.hongkong;

import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.order.OrderDetailReq;
import com.bh.live.pojo.req.order.OrderReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lgs
 * @title: LuckyAirshipOrderServiceImpl
 * @projectName java_live-cp
 * @description: 香港彩具体订单实现类
 * @date 2019/7/29  19:57
 */
@Component
@Slf4j
public class HongKongOrderValidator extends AbstractHongKongOrderValidator {


    @Override
    public Result<int[]> validatorContent(OrderReq req) {
        List<OrderDetailReq> detailReqs = req.getContent();
        final int[] betNum = {0};
        detailReqs.forEach(v ->  {
//            int num = analysisContent(v, k);
            v.setBetNum(CommonConstants.ONE);
            betNum[0] += 1;
        });
        return Result.success(betNum);
    }
}
