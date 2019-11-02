package com.bh.live.award.service.award.lottery.high.pkten;

import com.bh.live.award.service.award.lottery.high.pkten.AbstractFictitiousAward;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.trade.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WuLong
 * @date 2019/8/7 10:10
 * @description LuckyAirshipAward
 */
@Service
@Slf4j
public class LuckyAirshipAward extends AbstractFictitiousAward {

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
        resultOdds("40151,40152");
    }
}
