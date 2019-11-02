package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.OrderInfoDao;
import com.bh.live.award.service.IOrderInfoService;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.trade.OrderInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 投注订单表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfo> implements IOrderInfoService {

    @Override
    public List<OrderInfo> selectByOrderNos(List<String> orderNos,List<Integer> status) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(OrderInfo::getOrderNo,orderNos);
        if(CommonUtil.isNotEmpty(status)){
            queryWrapper.lambda().in(OrderInfo::getStatus,status);
        }
        return list(queryWrapper);
    }
}
