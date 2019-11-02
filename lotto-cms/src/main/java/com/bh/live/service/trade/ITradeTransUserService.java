package com.bh.live.service.trade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.trade.TradeTransUser;
import com.bh.live.model.trade.TradeTransUserData;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.TradeTransDetailRes;
import com.bh.live.pojo.res.trade.TradeTransExcelRes;

import java.util.List;

/**
 * <p>
 * 用户流水表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface ITradeTransUserService extends IService<TradeTransUser> {

    /**
     * 查询流水
     *
     * @param tradeTransUser
     * @return
     */
	TableDataInfo queryUserTransPage(TradeTransUser tradeTransUser);

    /**
     * 增加流水记录
     *
     * @param tradeTransUser
     * @return
     */
    boolean addUserTransFlow(TradeTransUser tradeTransUser);
    
    
    /**
     * 	查询流水数据
     *
     * @param tradeTransUser
     * @return
     */
    TradeTransUserData flowAllData(TradeTransUser tradeTransUser);
    
    
	/**
	 * 根据流水id查询详情
	 * 
	 * @param transId
	 * @return
	 */
	TradeTransDetailRes queryTransDetailById(String transId);

	/**
	 * 查询流水
	 *
	 * @param tradeTransUser
	 * @return
	 */
	List<TradeTransExcelRes> queryUserTransExcel(TradeTransUser tradeTransUser);

}
