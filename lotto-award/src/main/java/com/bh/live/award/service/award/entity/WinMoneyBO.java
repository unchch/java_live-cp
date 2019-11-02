package com.bh.live.award.service.award.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;
/**
 *@author WuLong
 *@date 2019/8/5 20:10
 *@description 中奖的订单派奖信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinMoneyBO implements Serializable {
	/**
	 *税前
	 */
	private Double preBonus;
	/**
	 *税后
	 */
	private Double aftBonus;
	/**
	 *加奖
	 */
	private Double addedBonus;
	/**
	 *总金额
	 */
	private Double totalMoney;
	/**
	 * 税后金额
	 */
	private Double totalAfterMoney;
	/**
	 *中奖详情
	 */
	private Map<String, Integer> winningDetail;

}
