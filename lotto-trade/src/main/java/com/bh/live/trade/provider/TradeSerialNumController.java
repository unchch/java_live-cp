package com.bh.live.trade.provider;

import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;
import com.bh.live.trade.service.IUserTradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lgs
 * @title: TradeSerNumController
 * @projectName java_live-cp
 * @description: 用户交易扣款流水
 * @date 2019/8/10  10:29
 */
@RestController
@RequestMapping("/serial-num")
@Api(tags = "竞猜模块：用户交易扣款流水")
public class TradeSerialNumController {

    @Autowired
    private IUserTradeService userTradeService;

    /**
     * 用户扣款
     *
     * @param req
     * @return
     */
    @PostMapping
    @ApiOperation(value = "用户中心-用户交易扣款流水", notes = "用户中心-用户交易扣款流水", response = String.class)
    public Object userTrade(@RequestBody TradeSerialNumReq req) {
        Assert.isNotNull(req.getUser(), CodeMsg.E_20004);
        Assert.isNotNull(req.getAmount(), CodeMsg.E_50108);
        Assert.isNotNull(req.getTransType(), CodeMsg.E_50109);
        return Result.success(userTradeService.updateUserWallet(req));
    }
}
