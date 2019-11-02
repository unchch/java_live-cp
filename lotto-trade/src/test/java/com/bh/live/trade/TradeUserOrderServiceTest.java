package com.bh.live.trade;

import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;
import com.bh.live.trade.service.ITradeUserOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author lgs
 * @title: TradeUserOrderServiceTest
 * @projectName java_live-cp
 * @description: TODO
 * @date 2019/8/8  18:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeUserOrderServiceTest {

    @Autowired
    private ITradeUserOrderService tradeUserOrderService;

    @Autowired
//    private ITradeFeignService tradeFeignService;

    @Test
    public void insertUserOrderTest() {
        LiveUser user = new LiveUser();
        user.setId(10005);
        tradeUserOrderService.insertUserOrder(null, user);
    }


    @Test
    public void tradeFeignTest() {
        LiveUser user = new LiveUser();
        user.setId(10005);
        TradeSerialNumReq req = new TradeSerialNumReq();
        req.setUser(user);
        req.setAmount(new BigDecimal(2));
        req.setTransType(2);
        req.setAddUserId(1);
//        tradeFeignService.userTrade(req);
//        tradeUserOrderService.insertUserOrder("O19080914453224300006", user);
    }
}
