package com.bh.live.award.service.award.lottery.low.hk;

import com.bh.live.award.service.award.entity.WinMoneyBO;
import com.bh.live.award.service.award.lottery.AbstractNumberAward;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderInfo;
import org.springframework.stereotype.Service;

/**
 *@author WuLong
 *@date 2019/8/6 10:57
 *@description Hk
 */
@Service
public class HkAward extends AbstractNumberAward {

    /**
     * 开奖号码
     */
    protected String[] drawCodeAll;

    @Override
    protected void handleDrawCode(String code) {

    }

    @Override
    protected void handleDrawDetail(String drawDetail) {

    }

    @Override
    protected boolean haveDrawCode() {
        return false;
    }

    @Override
    protected WinMoneyBO computeWinMoney(OrderInfo orderInfo) {
        return null;
    }

    @Override
    protected String getLevel(String prize) {
        return null;
    }

    @Override
    protected void compute(Order order, HandleEnum.PrizeStatusEnum prizeStatusEnum) {

    }

    @Override
    public void getResultOdds() {

    }
}
