package com.bh.live.trade;

import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;
import com.bh.live.trade.service.IUserTradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author lgs
 * @title: UserTradeServiceTest
 * @projectName java_live-cp
 * @description: 用户交易test
 * @date 2019/8/8  16:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTradeServiceTest {

    @Autowired
    private IUserTradeService userTradeService;

    @Test
    public void testUpdateUserWallet() {
//        LiveUser user = new LiveUser();
//        user.setId(10005);
//        BigDecimal amount = new BigDecimal(-1);
        LiveUser user = new LiveUser();
        user.setId(1);
        TradeSerialNumReq req = new TradeSerialNumReq();
        req.setUser(user);
        req.setAmount(new BigDecimal(2));
        req.setTransType(2);
        req.setAddUserId(1);

        userTradeService.updateUserWallet(req);
    }
}
