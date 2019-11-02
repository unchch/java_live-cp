package com.bh.live.trade.controller;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.trade.ProfitRateRankReq;
import com.bh.live.pojo.res.trade.ProfitRateRankRes;
import com.bh.live.trade.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/rank")
@Api(tags = "主播直播页面：竞猜排行")
public class RankController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    @ApiOperation(value = "用户中心-我的竞猜", notes = "用户中心-我的竞猜", response = ProfitRateRankRes.class)
    public Object selectRateRank(@RequestBody ProfitRateRankReq req) {
        return Result.success(orderService.selectRateRank(req));
    }

}
