package com.bh.live.trade.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.pojo.req.trade.AwardTradeReq;
import com.bh.live.pojo.req.trade.TradeUserReq;
import com.bh.live.pojo.res.trade.TradeAllTransUser;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.trade.Order;
import com.bh.live.pojo.res.trade.TradeTransUserRes;

/**
 * <p>
 * 用户流水表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface TradeTransUserDao extends BaseMapper<TradeTransUserRes> {

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

	/**
	 * 查询用户流水
	 * @param req
	 * @return
	 */
	public List<TradeAllTransUser> queryTradeAllTransUser(Page<TradeAllTransUser> iPage,@Param("req") AwardTradeReq req);

	/**
	 * 查询用户流水总数
	 * @param req
	 * @return
	 */
	public Integer queryTradeAllTransUserCount(@Param("req") AwardTradeReq req);

}
