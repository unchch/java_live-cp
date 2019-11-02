package com.bh.live.award.service.award.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 开奖结果
 * @author WuLong
 * @date 2019/8/5 20:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardResultBO implements Serializable{

	private static final long serialVersionUID = 2068599726240365275L;
	/**
	 *成功数
	 */
	private Integer success;
	/**
	 *失败数
	 */
	private Integer fail;
	/**
	 *开奖失败订单
	 */
	private List<String> failOrder;
	/**
	 *已开奖时间
	 */
	private Long awardSecond;
	/**
	 *预计剩余开奖时间
	 */
	private Long planSecond;
	/**
	 *总订单数
	 */
	private Integer total;
	/**
	 *开奖号码
	 */
	private String drawCode;
	/**
	 *开奖错误信息
	 */
	private List<String> failMessage;
	/**
	 *中奖订单
	 */
	private Integer winCount;


	
}
