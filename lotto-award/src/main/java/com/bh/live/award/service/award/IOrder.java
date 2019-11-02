package com.bh.live.award.service.award;

import com.bh.live.award.service.award.entity.AwardResultBO;
import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.model.trade.Order;

import java.util.List;

/**
 *@author WuLong
 *@date 2019/8/5 20:16
 *@description 订单操作接口
 */
public interface IOrder {
	/**
	 *@description 分配订单进行开奖
	 *@author WuLong
	 *@date 2019/8/5 20:16
	 *@param orders 订单集合
	 *@param seedNo 彩种编号
	 *@param issueNo 彩期
	 *@param prizeStatusEnum {@link HandleEnum.AwardTypeEnum} 开奖枚举
	 *@param type {@link HandleEnum.LotteryTypeEnum} 彩种类型
	 *@return
	 *@exception
	 */
	void  distribute(List<Order> orders, Integer seedNo, String issueNo, Integer type, HandleEnum.PrizeStatusEnum prizeStatusEnum);
	/**
	 * @Description: 查询需要开奖的推荐订单进行开奖
	 * @param lotteryCode
	 * @param lotteryChild
	 * @param type 1:重置开奖，0：开奖
	 * @param orderStatus
	 * @param systemCode 重置的赛事编号
	 * @author WuLong
	 * @date 2019/8/5 20:08
	 */
	void RecommendDistribute(Integer lotteryCode, Integer lotteryChild, Integer orderStatus, Integer type, String systemCode);
	/**
	 * 获取实时开奖结果
	 * @author WuLong
	 * @date 2019/8/5 20:08
	 * @param lotteryCode
	 * @param issue
	 * @return
	 */
	AwardResultBO getResult(int lotteryCode, String issue, Integer handleType);
}
