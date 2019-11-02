package com.bh.live.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.pojo.req.trade.AwardTradeReq;
import com.bh.live.pojo.req.trade.TradeTransUserReq;
import com.bh.live.pojo.req.trade.TradeUserReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.TradeAllTransUser;
import com.bh.live.pojo.res.trade.TradeTransDetailRes;
import com.bh.live.pojo.res.trade.TradeTransUserRes;

import java.util.List;

/**
 * <p>
 * 用户流水表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface ITradeTransUserService extends IService<TradeTransUserRes> {

	/**
	 * 查询流水
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param tradeTransUser
	 * @return
	 */
	IPage<TradeTransUserRes> queryUserTransPage(int pageNum, int pageSize, TradeTransUserReq tradeTransUser);

	/**
	 * 增加流水记录
	 *
	 * @param tradeTransUser
	 * @return
	 */
	int addUserTransFlow(TradeTransUserReq tradeTransUser);

	/**
	 * 根据条件查询流水分页
	 * 
	 * @return
	 */
	public IPage<TradeTransUserRes> queryTransPage(TradeUserReq req);

	/**
	 * 根据流水id查询详情
	 * 
	 * @param transId
	 * @return
	 */
	public TradeTransDetailRes queryTransDetailById(String transId);

	/**
	 * 打赏流水
	 * @return
	 */
	public TableDataInfo queryTradeAllTransUser(AwardTradeReq req);

}
