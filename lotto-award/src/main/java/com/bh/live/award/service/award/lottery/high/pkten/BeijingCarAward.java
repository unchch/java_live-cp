package com.bh.live.award.service.award.lottery.high.pkten;

import com.bh.live.award.service.award.lottery.high.pkten.AbstractFictitiousAward;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.trade.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WuLong
 * @date 2019/8/6 16:33
 * @description bj赛车
 */
@Service
public class BeijingCarAward extends AbstractFictitiousAward {

    @Override
    protected boolean haveDrawCode() {
        return CommonUtil.isNotEmpty(drawCodes);
    }

    @Override
    protected boolean havePlay() {
        return CommonUtil.isNotEmpty(playMap);
    }

    @Override
    protected boolean havePlayBit() {
        return CommonUtil.isNotEmpty(playBitMap);
    }

    @Override
    protected boolean havePlayBet() {
        return CommonUtil.isNotEmpty(playBetMap);
    }

    @Override
    protected boolean havePlayItem() {
        return CommonUtil.isNotEmpty(playItemMap);
    }

    @Override
    protected boolean haveResultOdds() {
        return CommonUtil.isNotEmpty(matchLocationResultMap)&&CommonUtil.isNotEmpty(matchSingleDoubleResultMap)
                &&CommonUtil.isNotEmpty(matchBigSmallResultMap)&&CommonUtil.isNotEmpty(matchDragonTigerResultMap);
    }

    @Override
    protected void getDrawCode(List<Order> orders) {
        super.getDrawCode(orders);
    }

    @Override
    public void getResultOdds() {
        resultOdds("40251,40252");
    }
}
