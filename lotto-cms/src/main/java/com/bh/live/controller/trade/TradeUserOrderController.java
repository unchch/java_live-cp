package com.bh.live.controller.trade;


import com.bh.live.common.constant.JxlsTemplateConst;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.jxls.JxlsExcelUtil;
import com.bh.live.model.trade.OrderCount;
import com.bh.live.model.trade.OrderView;
import com.bh.live.model.trade.UserBuyOrder;
import com.bh.live.model.trade.vo.OrderVO;
import com.bh.live.model.trade.vo.UserBuyOrderVO;
import com.bh.live.service.trade.IOrderService;
import com.bh.live.service.trade.ITradeUserOrderService;
import com.bh.live.utils.PropUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
@Api(tags = "竞猜管理：用户购买竞猜订单列表")
@Slf4j
public class TradeUserOrderController extends BaseController {

    @Autowired
    private ITradeUserOrderService tradeUserOrderService;

    @Autowired
    private IOrderService orderService;

    @PostMapping("/page")
    @ApiOperation(value = "用户购买竞猜订单列表", response = OrderView.class)
    @ApiResponses(value = {
            @ApiResponse(response = UserBuyOrder.class, code = 0, message = "订单列表"),
            @ApiResponse(response = OrderCount.class, code = 1, message = "订单统计")
    })
    public Object page(@RequestBody UserBuyOrderVO vo) {

        OrderView view = new OrderView();
        view.setPage(tradeUserOrderService.selectUserByOrder(vo));

        OrderVO orderVO = new OrderVO();
        orderVO.setSeedNo(vo.getSeedNo())
                .setStartTime(vo.getStartTime())
                .setEndTime(vo.getEndTime());
        view.setOrderCount(orderService.selectOrderCount(orderVO));
        return Result.success(view);
    }


    @GetMapping("/export")
    @ApiOperation(value = "导出用户购买竞猜订单列表")
    public void export(UserBuyOrderVO vo, HttpServletResponse response) {
        new JxlsExcelUtil<>(PropUtil.fileExportUrl, tradeUserOrderService.selectUserByOrderList(vo), JxlsTemplateConst.ORDER_BUY_TEMPLATE, response);
    }
}

