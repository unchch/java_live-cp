package com.bh.live.trade.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.TradeUserOrder;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeUserOrderReq;
import com.bh.live.pojo.res.trade.AwardOrderRes;
import com.bh.live.pojo.res.trade.TradeUserOrderRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户购买记录 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface ITradeUserOrderService extends IService<TradeUserOrder> {

    /**
     * 查询用户
     * @param userWrapper
     * @return
     */
    List<TradeUserOrderRes> selectUserByOrder(@Param(Constants.WRAPPER) Wrapper<TradeUserOrderReq> userWrapper);

    /**
     * 查询用户购买几率条数
     * @param userWrapper
     * @return
     */
    int selectOrderCount(@Param(Constants.WRAPPER) Wrapper<TradeUserOrderReq> userWrapper);

    /**
     * 用户购买竞猜
     * @param order
     * @param user
     * @return
     */
    int insertUserOrder(Order order, LiveUser user);

    /**
     * 根据彩期彩种查询数据
     * @param map
     * @return
     */
    List<AwardOrderRes> updateAwardOrder(Map<String, Object> map);
}
