package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.OrderInfoDetailDao;
import com.bh.live.award.service.IOrderInfoDetailService;
import com.bh.live.model.trade.OrderInfoDetail;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 投注订单明细表(派奖后数据插入) 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class OrderInfoDetailServiceImpl extends ServiceImpl<OrderInfoDetailDao, OrderInfoDetail> implements IOrderInfoDetailService {

}
