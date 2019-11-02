package com.bh.live.user;

import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;
import com.bh.live.rpc.service.trade.ITradeFeignService;
import com.bh.live.user.service.ITradeUserAwardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LottoTradeUserAwardTests {
    @Autowired
    ITradeUserAwardService tradeUserAwardService;

    @Autowired
    private ITradeFeignService tradeFeignService;
    /**
     * 测试添加
     */
    @Test
    public void testAdd() {
       /* TradeUserAwardReq req = new TradeUserAwardReq();
        req.setUserId(1);
        req.setTvUserId(3);
        req.setGiftId(3);
        req.setGiftNum(520);
        req.setGiftAmount(new BigDecimal(100));
        req.setRoomId(1);
        req.setLottoId(1);
        req.setAmount(new BigDecimal(52000));
        Integer integer = tradeUserAwardService.add(req);
        System.out.println(integer);*/
    }

    /**
     * 查询列表
     */
    @Test
    public void getList() {
       /* TableDataInfo tableDataInfo =tradeUserAwardService.getList(1,1,10);
        tableDataInfo.getList().forEach(System.out::println);*/
    }


    @Test
    public void tradeFeignTest() {
        LiveUser user = new LiveUser();
        user.setId(10005);
        TradeSerialNumReq req = new TradeSerialNumReq();
        req.setUser(user);
        req.setAmount(new BigDecimal(2));
        req.setTransType(TransEnum.TransTypeEnum.REWARD.getCode());
        System.out.println("::::::::::::::::::::"+tradeFeignService.userTrade(req));
//        tradeUserOrderService.insertUserOrder("O19080914453224300006", user);
    }

}
