package com.bh.live.controller.trade;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bh.live.common.constant.JxlsTemplateConst;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.jxls.JxlsExcelUtil;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderCount;
import com.bh.live.model.trade.OrderView;
import com.bh.live.model.trade.vo.OrderVO;
import com.bh.live.service.trade.IOrderService;
import com.bh.live.utils.PropUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author lgs
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/trade/order")
@Api(tags = "竞猜管理：竞猜订单")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @PostMapping()
    @ApiOperation(value = "列表", response = OrderView.class)
    @ApiResponses(value = {
            @ApiResponse(response = Order.class, code = 0, message = "订单列表"),
            @ApiResponse(response = OrderCount.class, code = 1, message = "订单统计")
    })
    public Object page(@RequestBody OrderVO vo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Order order = new Order();
        BeanUtils.copyProperties(vo, order);
        queryWrapper.setEntity(order);
        OrderView view = new OrderView();
        view.setPage(orderService.selectOrder(vo));
        view.setOrderCount(orderService.selectOrderCount(vo));
        return Result.success(view);
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出竞猜订单列表")
    public void export(OrderVO vo, HttpServletResponse response) {
        new JxlsExcelUtil<>(PropUtil.fileExportUrl, orderService.selectOrderList(vo), JxlsTemplateConst.ORDER_TEMPLATE, response);
    }
}

