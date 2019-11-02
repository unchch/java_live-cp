package com.bh.live.trade.service;

import com.bh.live.common.enums.lottery.LotteryEnum;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.trade.service.impl.hongkong.HongKongOrderValidator;
import com.bh.live.trade.service.impl.lucky.LuckyAirshipOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lgs
 * @title: ValidatorGentcen
 * @projectName java_live-cp
 * @description: 设置验证类型
 * @date 2019/7/31  15:27
 */
@Component
public class ValidatorGenerator {

    @Autowired
    private LuckyAirshipOrderValidator luckyAirshipOrderValidator;

    @Autowired
    private HongKongOrderValidator hongKongOrderValidator;

    /**
     * 验证订单
     *
     * @param orderReq
     * @return
     */
    public Result<Map<String,Object>> validator(OrderReq orderReq) {
        Validator validator = null;

        switch (LotteryEnum.LottoSeedNoEnum.getLottoSeed(orderReq.getLotteryCode())) {
            case LUCKY_AIRSHIP:
            case BJ_CAR:
                validator = luckyAirshipOrderValidator;
                break;
            case HONG_KONG:
                validator = hongKongOrderValidator;
                break;
            case CQ_SSC:
                validator = hongKongOrderValidator;
                break;
            default:

        }
        return validator.handle(orderReq);
    }

}
