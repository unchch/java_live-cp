package com.bh.live.trade.service.impl.lucky;

import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.lottery.LotteryPlayEnum;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.Play;
import com.bh.live.pojo.req.order.OrderDetailReq;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.trade.service.AbstractOrderDetailValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lgs
 * @title: AbstractLuckyOrderValidator
 * @projectName java_live-cp
 * @description: 幸运飞艇 北京赛车车抽象 公共方法。
 * @date 2019/7/29  19:39
 */
@Slf4j
public abstract class AbstractLuckyOrderValidator extends AbstractOrderDetailValidator {

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
        switch (LotteryPlayEnum.getLotteryPlay(play.getPlayNo())) {
            case LUCKY_AIRSHIP_40151:
            case BJ_CAR_40251:
            case LUCKY_AIRSHIP_CHAMPION:
            case LUCKY_AIRSHIP_RUNNER_UP:
            case LUCKY_AIRSHIP_THIRD:
            case LUCKY_AIRSHIP_FOURTH_PLACE:
            case LUCKY_AIRSHIP_FIFTH_PLACE:
            case LUCKY_AIRSHIP_SIXTH_PLACE:
            case LUCKY_AIRSHIP_SEVENTH_PLACE:
            case LUCKY_AIRSHIP_EIGHTH_PLACE:
            case LUCKY_AIRSHIP_NINTH_PLACE:
            case LUCKY_AIRSHIP_TENTH_PLACE:
            case BJ_CAR_CHAMPION:
            case BJ_CAR_RUNNER_UP:
            case BJ_CAR_THIRD:
            case BJ_CAR_FOURTH_PLACE:
            case BJ_CAR_FIFTH_PLACE:
            case BJ_CAR_SIXTH_PLACE:
            case BJ_CAR_SEVENTH_PLACE:
            case BJ_CAR_EIGHTH_PLACE:
            case BJ_CAR_NINTH_PLACE:
            case BJ_CAR_TENTH_PLACE:
                betNum = content.split(SymbolConstants.COMMA).length;
                break;
            case LUCKY_AIRSHIP_SUM:
            case LUCKY_AIRSHIP_CHAMPION_RUNNER_UP:
            case LUCKY_AIRSHIP_DT:
            case LUCKY_AIRSHIP_CHAMPION_INDEX:
            case LUCKY_AIRSHIP_CHAMPION_RUNNER_UP_SD:
            case LUCKY_AIRSHIP_TOP_3:
            case LUCKY_AIRSHIP_SIZE:
            case LUCKY_AIRSHIP_SD:
            case BJ_CAR_SUM:
            case BJ_CAR_CHAMPION_RUNNER_UP:
            case BJ_CAR_DT:
            case BJ_CAR_CHAMPION_INDEX:
            case BJ_CAR_CHAMPION_RUNNER_UP_SD:
            case BJ_CAR_TOP_3:
            case BJ_CAR_SIZE:
            case BJ_CAR_SD:
                if (content.length() != 1) {
                    throw new ResultJsonException(CodeMsg.E_50023);
                }
                betNum = 1;
                break;
            default:
                throw new ResultJsonException(CodeMsg.E_50022);
        }
        return betNum;
    }


}
