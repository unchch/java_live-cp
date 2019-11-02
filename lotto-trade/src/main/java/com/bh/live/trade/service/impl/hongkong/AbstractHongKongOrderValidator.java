package com.bh.live.trade.service.impl.hongkong;

import cn.hutool.core.util.StrUtil;
import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.enums.lottery.LotteryPlayEnum;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.ArrayUtil;
import com.bh.live.common.utils.CalculatorUtil;
import com.bh.live.model.lottery.Play;
import com.bh.live.pojo.req.order.OrderDetailReq;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.trade.service.AbstractOrderDetailValidator;

/**
 * @author lgs
 * @title: AbstractHongKongOrderValidator
 * @projectName java_live-cp
 * @description: 香港彩验证
 * @date 2019/7/31  20:04
 */
public abstract class AbstractHongKongOrderValidator extends AbstractOrderDetailValidator {

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
        String[] num = StrUtil.split(content, SymbolConstants.COMMA);
        Assert.isFalse(ArrayUtil.isRepeat(num), CodeMsg.E_50025);
        switch (LotteryPlayEnum.getLotteryPlay(play.getPlayNo())) {
            case HONEGKONG_SPECIAL_CODE:
            case HONEGKONG_NUM:
            case HONEGKONG_FLAT_CODE:
            case HONEGKONG_FLAT_CODE_ESPECIALLY:
            case HONEGKONG_FLAT_CODE_ESPECIALLY_ONE:
            case HONEGKONG_FLAT_CODE_ESPECIALLY_TWO:
            case HONEGKONG_FLAT_CODE_ESPECIALLY_THREE:
            case HONEGKONG_FLAT_CODE_ESPECIALLY_FOUR:
            case HONEGKONG_FLAT_CODE_ESPECIALLY_FIVE:
            case HONEGKONG_FLAT_CODE_ESPECIALLY_SIX:
                betNum = num.length;
                break;
            case HONEGKONG_ZODIAC:
            case HONEGKONG_SPECIAL_ZODIAC:
            case HONEGKONG_FLAT_ZODIAC:
            case HONEGKONG_ONE_ZODIAC:
            case HONEGKONG_TOTAL_ZODIAC:
                Assert.isTrue(num.length <= CommonConstants.TWELVE, CodeMsg.E_50026);
                betNum = num.length;
                break;
            case HONEGKONG_TWO_SIDES:
            case HONEGKONG_WAVE_COLOR:
                Assert.isTrue(num.length == CommonConstants.ONE, CodeMsg.E_50023);
                betNum = 1;
                break;
            case HONEGKONG_THREE_PASS:
            case HONEGKONG_THREE_PASS_TWO:
                /*三全中 三中二最小选择3个选项*/
                Assert.isFalse(num.length < CommonConstants.THREE, CodeMsg.E_50023);
                betNum = CalculatorUtil.combination(num.length, CommonConstants.THREE);
                break;
            case HONEGKONG_TWO_PASS:
            case HONEGKONG_TWO_PASS_SPECIAL:
            case HONEGKONG_SPECIAL_PASS:
                /*二全中 二中特 特串 最小选择2个选项*/
                Assert.isFalse(num.length < CommonConstants.TWO, CodeMsg.E_50023);
                betNum = CalculatorUtil.combination(num.length, CommonConstants.TWO);
                break;
            default:
                throw new ResultJsonException(CodeMsg.E_50022);
        }
        return betNum;
    }


}
