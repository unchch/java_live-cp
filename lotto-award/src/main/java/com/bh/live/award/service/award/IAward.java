package com.bh.live.award.service.award;

import com.bh.live.award.service.award.entity.AwardOrderBO;
import com.bh.live.award.service.award.entity.FailOrderBO;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.model.trade.Order;

import java.util.List;
import java.util.Map;

/**
 *@author WuLong
 *@date 2019/8/5 20:14
 *@description 开奖
 */
public interface IAward {
	/**
	 *@description 开奖
	 *@author WuLong
	 *@date 2019/8/5 20:14
	 *@param orders 订单号
	 *@return  开奖失败订单信息
	 */
	AwardOrderBO handle(List<Order> orders, boolean isBonus, int type, HandleEnum.PrizeStatusEnum prizeStatusEnum) throws ServiceRuntimeException;
	/**
	 *@description 获取执行时间
	 *@author WuLong
	 *@date 2019/8/5 20:15
	 *@return
	 */
	Map<String,Long> getExecuteTime() throws ServiceRuntimeException;
	/**
	 *@description 获取开奖号码
	 *@author WuLong
	 *@date 2019/8/5 20:15
	 *@return
	 */
	String getDrawCode() throws ServiceRuntimeException;
}
