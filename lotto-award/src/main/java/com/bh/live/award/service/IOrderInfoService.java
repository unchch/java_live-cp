package com.bh.live.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.trade.OrderInfo;

import java.util.List;

/**
 * <p>
 * 投注订单明细表 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    /**
     *@description 查询投注订单明细表
     *@author WuLong
     *@date 2019/8/6 16:48
     *@param
     *@return
     *@exception
     */
    List<OrderInfo> selectByOrderNos(List<String> orderNos,List<Integer> status);
}
