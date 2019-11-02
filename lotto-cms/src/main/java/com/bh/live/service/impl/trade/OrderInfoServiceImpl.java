package com.bh.live.service.impl.trade;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.trade.OrderInfoDao;
import com.bh.live.model.trade.OrderInfo;
import com.bh.live.service.trade.IOrderInfoService;
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
