package com.bh.live.rpc.service.trade;

import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;
import com.bh.live.pojo.res.trade.GuessingDetailRes;
import com.bh.live.pojo.res.trade.GuessingRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "lotto-trade", fallbackFactory = TradeFeignServiceFallback.class)
public interface ITradeFeignService {

    /**
     * 竞猜未开奖接口
     *
     * @param seedNo
     * @param lotIssueNo
     * @return
     */
    @GetMapping("/trade/order/getGuessing")
    List<GuessingRes> getGuessing(@RequestParam("seedNo") Integer seedNo, @RequestParam("lotIssueNo") String lotIssueNo);

    /**
     * 竞猜详情接口
     *
     * @param id
     * @return
     */
    @GetMapping("/trade/order/guessingDetail")
    GuessingDetailRes guessingDetail(@RequestParam("id") Integer id, @RequestParam("userId") Integer userId);


    /**
     * 交易扣款接口
     *
     * @param req
     * @return
     */
    @GetMapping("/trade/serial-num")
    Result userTrade(@RequestBody TradeSerialNumReq req);
}
