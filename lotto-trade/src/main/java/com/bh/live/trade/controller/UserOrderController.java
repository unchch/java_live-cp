package com.bh.live.trade.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.trade.Order;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.trade.OrderInfoContentRes;
import com.bh.live.trade.service.IOrderService;
import com.bh.live.trade.service.ITradeUserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lgs
 * @title: UserOrder
 * @projectName java_live-cp
 * @description: 用户购买订单
 * @date 2019/8/8  15:29
 */
@RestController
@RequestMapping("/order/buy")
@Api(tags = "竞猜模块：用户购买竞猜")
public class UserOrderController extends BaseController {

    @Autowired
    private ITradeUserOrderService tradeUserOrderService;

    @Autowired
    private IOrderService orderService;

    /**
     * 购买竞猜
     *
     * @param orderNo
     * @return
     */
    @GetMapping
    @ApiOperation(value = "竞猜模块：用户购买竞猜", notes = "竞猜模块：用户购买竞猜", response = OrderInfoContentRes.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号:任意传一个", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "订单id:任意传一个", required = false, dataType = "string", paramType = "query")})
    public Object userBuyOrder(@RequestParam(value = "orderNo", required = false) String orderNo, @RequestParam(value = "id", required = false) Integer id) {
        Assert.isTrue(StrUtil.isNotEmpty(orderNo) || ObjectUtil.isNotEmpty(id), CodeMsg.E_50105);
        LiveUser user = getHeaderLiveUser();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(orderNo)) {
            queryWrapper.eq(Order::getOrderNo, orderNo);
        }
        if (ObjectUtil.isNotEmpty(id)) {
            queryWrapper.eq(Order::getId, id);
        }
        Order order = orderService.getOne(queryWrapper);
        Assert.isTrue(ObjectUtil.isNotEmpty(order), CodeMsg.E_50105);
        int num = tradeUserOrderService.insertUserOrder(order, user);
        if (num > 0) {
            return Result.success(orderService.selectOrderInfo(order));
        }
        return Result.success(num);
    }

}
