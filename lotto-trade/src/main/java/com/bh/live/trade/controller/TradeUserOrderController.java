package com.bh.live.trade.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bh.live.common.result.Result;
import com.bh.live.model.trade.UserBuyOrder;
import com.bh.live.pojo.req.trade.TradeUserOrderReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.trade.service.ITradeUserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户购买记录 前端控制器
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@RestController
@RequestMapping("/trade-user-order")
@Api("竞猜管理：用户购买竞猜订单列表")
@Slf4j
public class TradeUserOrderController {

    @Autowired
    private ITradeUserOrderService tradeUserOrderService;

    @GetMapping("/page")
    @ApiOperation(value = "用户购买竞猜订单列表", response = UserBuyOrder.class)
    public Object page(@RequestBody TradeUserOrderReq vo) {
        QueryWrapper<TradeUserOrderReq> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(vo.getIsPay())) {
            queryWrapper.lambda().eq(TradeUserOrderReq::getIsPay, vo.getIsPay());
        }
        if (ObjectUtil.isNotEmpty(vo.getAccountId())) {
            queryWrapper.lambda().eq(TradeUserOrderReq::getAccountId, vo.getAccountId());
        }
        if (ObjectUtil.isNotEmpty(vo.getUserId())) {
            queryWrapper.lambda().eq(TradeUserOrderReq::getUserId, vo.getUserId());
        }
        if (StrUtil.isNotEmpty(vo.getOrderNo())) {
            queryWrapper.lambda().eq(TradeUserOrderReq::getOrderNo, vo.getOrderNo());
        }
        if (StrUtil.isNotEmpty(vo.getSerialCode())) {
            queryWrapper.lambda().eq(TradeUserOrderReq::getSerialCode, vo.getSerialCode());
        }
        if (StrUtil.isNotEmpty(vo.getLotIssueNo())) {
            queryWrapper.lambda().eq(TradeUserOrderReq::getLotIssueNo, vo.getLotIssueNo());
        }
        if (StrUtil.isNotEmpty(vo.getAccountName())) {
            queryWrapper.lambda().like(TradeUserOrderReq::getAccountName, vo.getAccountName());
        }
        if (ObjectUtil.isNotEmpty(vo.getLotSeedNo())) {
            queryWrapper.lambda().eq(TradeUserOrderReq::getLotSeedNo, vo.getLotSeedNo());
        }
        if (ObjectUtil.isNotEmpty(vo.getBeginTime())) {
            queryWrapper.lambda().ge(TradeUserOrderReq::getCreateTime, vo.getBeginTime());
        }
        if (ObjectUtil.isNotEmpty(vo.getEndTime())) {
            queryWrapper.lambda().le(TradeUserOrderReq::getCreateTime, vo.getEndTime());
        }
        TableDataInfo tableDataInfo = new TableDataInfo();
        int count = tradeUserOrderService.selectOrderCount(queryWrapper);
        if (count > 0) {
            tableDataInfo.setTotalNumber(count);
            tableDataInfo.setList(tradeUserOrderService.selectUserByOrder(queryWrapper));
        }
        return Result.success(tableDataInfo);
    }

}

