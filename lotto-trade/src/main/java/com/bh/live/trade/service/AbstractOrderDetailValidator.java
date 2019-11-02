package com.bh.live.trade.service;

import cn.hutool.core.map.MapUtil;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.Play;
import com.bh.live.model.lottery.PlayBet;
import com.bh.live.model.lottery.PlayBit;
import com.bh.live.model.lottery.PlayItem;
import com.bh.live.pojo.req.order.OrderReq;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lgs
 * @title: AbstractOrderDetailValidator
 * @projectName java_live-cp
 * @description: 提供公共验证
 * @date 2019/7/19  10:53
 */
@Slf4j
public abstract class AbstractOrderDetailValidator extends BaseValidateService implements Validator {


    /**
     * 订单公共验证类
     *
     * @param req 订单内容
     * @return
     */
    public abstract Result<?> handleProcess(OrderReq req);


    /**
     * 订单公共验证类
     *
     * @param req 订单内容明细
     * @return
     */
    public abstract Result<int[]> validatorContent(OrderReq req);

    @Override
    public Result<Map<String, Object>> handle(OrderReq orderReq) {
        log.info("彩种 验证开始");
        String content = validateLottery(orderReq);
        Result<int[]> betNumResult = validatorContent(orderReq);
        log.info("彩种 验证结束订单一共{}注", betNumResult.getData()[0]);
        Map<String, Object> resultMap = MapUtil.newHashMap();
        resultMap.put("content", content);
        resultMap.put("betNum", betNumResult);
        return Result.success(resultMap);
    }


    /**
     * 验证彩种状态
     *
     * @param req
     * @return 展示内容
     */
    public String validateLottery(OrderReq req) {
        verifySaleStatus(req.getLotteryCode());

        List<Play> plays = getLotteryPay(req.getLotteryCode());
        //字符内容
        Map<String, String> bitMap = MapUtil.newHashMap();
        Map<String, String> contentMap = MapUtil.newHashMap();
        req.getContent().forEach(v -> {
            //验证玩法
            Result<Play> playResult = verifyLotteryPaySaleStatus(req.getLotteryCode(), v.getPlayNo());
            v.setPlayName(playResult.getData().getPlayName());
            //验证投注位
            Result<PlayBit> playBitResult = verifyPlayBitStatus(v.getBitNo());
            //验证投注项
            Result<PlayItem> playItemResult = verifyPlayItemStatus(v.getItemNo());
            v.setBitNo(playBitResult.getData().getBitNo());
            v.setContent(playItemResult.getData().getContent());
            String key = playResult.getData().getPlayNo();
            bitMap.put(key, playResult.getData().getPlayName());
            joinContent(contentMap, key, playItemResult);
            PlayBet bet = getPlayBets(playResult.getData().getPlayNo(), playItemResult.getData().getItemNo());
            v.setOdds(bet.getOdds());
        });
        return joinContent(contentMap, bitMap);
    }


    /**
     * 拼接投注显示内容
     *
     * @param contentMap
     * @return
     */
    public void joinContent(Map<String, String> contentMap, String key, Result<PlayItem> playItemResult) {
        String content = "";
        PlayItem item = playItemResult.getData();
        if (contentMap.containsKey(key)) {
            content = contentMap.get(key);
            content = content + " " + item.getContent();
        } else {
            content = item.getContent();
        }
        contentMap.put(key, content);
    }


    /**
     * 拼接投注显示内容
     *
     * @param contentMap
     * @return
     */
    public String joinContent(Map<String, String> contentMap, Map<String, String> bitMap) {
        final String[] content = {""};
        Map<String, String> bitMapResult = sortByKey(bitMap);
        bitMapResult.forEach((k, v) -> content[0] += SymbolConstants.MIDDLE_PARENTHESES_LEFT + v + SymbolConstants.MIDDLE_PARENTHESES_RIGHT + " " + contentMap.get(k) + " ");
        return content[0];
    }

    public <K extends Comparable<? super K>, V > Map<K, V> sortByKey(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

}
