package com.bh.live.trade.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bh.live.model.trade.TradeUserOrder;
import com.bh.live.pojo.req.trade.TradeUserOrderReq;
import com.bh.live.pojo.res.trade.AwardOrderRes;
import com.bh.live.pojo.res.trade.TradeUserOrderRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户购买记录 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface TradeUserOrderDao extends BaseMapper<TradeUserOrder> {

    /**
     * 查询用户
     *
     * @param userWrapper
     * @return
     */
    List<TradeUserOrderRes> selectUserByOrder(@Param(Constants.WRAPPER) Wrapper<TradeUserOrderReq> userWrapper);

    /**
     * 查询用户购买几率条数
     *
     * @param userWrapper
     * @return
     */
    int selectOrderCount(@Param(Constants.WRAPPER) Wrapper<TradeUserOrderReq> userWrapper);

    /**
     * 根据彩种彩期查询需要派奖订单
     * @param issue
     * @param seedNo
     * @return
     */
    List<AwardOrderRes> selectAwardOrderRes(@Param("issue") String issue, @Param("seedNo") Integer seedNo);
}
