package com.bh.live.dao.trade;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.TradeTransUser;

/**
 * <p>
 * 用户流水表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface TradeTransUserDao extends BaseMapper<TradeTransUser> {

	/**
	 * 查询订单
	 * 
	 * @param orderno
	 * @return
	 */
	public Order queryOrderByOrderno(@Param("orderno") String orderno);

	/**
	 * 查询礼物
	 * @param orderno
	 * @return
	 */
	public Map<String,String> queryGif(@Param("orderno") String orderno);

}
