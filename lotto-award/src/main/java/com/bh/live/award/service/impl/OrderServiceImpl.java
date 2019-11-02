package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.OrderDao;
import com.bh.live.award.service.IOrderInfoService;
import com.bh.live.award.service.IOrderService;
import com.bh.live.common.enums.trade.OrderEnum;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements IOrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private IOrderInfoService orderInfoService;

    @Override
    public List<Order> getOrderInfoBos(List<String> orders,List<Integer> status) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(Order::getOrderNo,orders);
        queryWrapper.lambda().in(Order::getStatus,status);
        return list(queryWrapper);
    }

    @Override
    public List<Order> getOrderByIssueNoSeedNoStatus(String issueNo, Integer seedNo, List<Integer> status) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getLotIssueNo, issueNo).eq(Order::getLotSeedNo, seedNo).in(Order::getStatus, status);
        return list(queryWrapper);
    }

    @Override
    public List<String> selectDrawCodeOrderNos(Integer seedNo, String issueNo, List<Integer> status) {
        return orderDao.selectDrawCodeOrderNos(seedNo,issueNo,status);
    }

    @Override
    public void updateOrderDrawFail(List<String> failOrders) {
        if (failOrders.isEmpty()) {
            return;
        }
        List<Order> orderInfoBos = getOrderInfoBos(failOrders, null);
        if(CommonUtil.isNotEmpty(orderInfoBos)){
            List<Order> updateOrders = new ArrayList<>();
            for (Order order : orderInfoBos) {
                if(!order.getStatus().equals(OrderEnum.OrderStatusEnum.HAVE_BEEN_AWARDED.getCode())){
                    order.setProfitAmount(BigDecimal.ZERO);
                    order.setAwardAmount(BigDecimal.ZERO);
                    order.setAwardState(0);
                    order.setAwardNumber(null);
                    order.setStatus(OrderEnum.OrderStatusEnum.PENDING_AWARD.getCode());
                    order.setProfitRate(BigDecimal.ZERO);
                    order.setWinQuantity(null);
                    order.setPrizeStatus(2);
                    order.setLotteryTime(new Date());
                    updateOrders.add(order);
                }
            }
            if(CommonUtil.isNotEmpty(updateOrders)){
                updateBatchById(updateOrders);
            }
        }
        List<OrderInfo> orderInfoList = orderInfoService.selectByOrderNos(failOrders, null);
        if(CommonUtil.isNotEmpty(orderInfoBos)){
            List<OrderInfo> updateOrderInfos = new ArrayList<>();
            for(OrderInfo orderInfo : orderInfoList){
                if(!orderInfo.getStatus().equals(OrderEnum.OrderStatusEnum.HAVE_BEEN_AWARDED.getCode())){
                    orderInfo.setAwardState(0);
                    orderInfo.setAwardAmount(BigDecimal.ZERO);
                    orderInfo.setProfitAmount(BigDecimal.ZERO);
                    orderInfo.setStatus(OrderEnum.OrderStatusEnum.PENDING_AWARD.getCode());
                    orderInfo.setWinQuantity(null);
                    orderInfo.setPrizeStatus(2);
                    orderInfo.setRemark("开奖数据重置");
                    updateOrderInfos.add(orderInfo);
                }
            }
            if(CommonUtil.isNotEmpty(updateOrderInfos)){
                orderInfoService.updateBatchById(updateOrderInfos);
            }
        }
    }



}
