package com.bh.live.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.trade.OrderInfo;
import com.bh.live.trade.dao.OrderInfoDao;
import com.bh.live.trade.service.IOrderInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 投注订单表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-26
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfo> implements IOrderInfoService {

}
