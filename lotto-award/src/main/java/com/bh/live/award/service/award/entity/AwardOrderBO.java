package com.bh.live.award.service.award.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *@author WuLong
 *@date 2019/8/5 20:08
 *@description 开奖成功和失败
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardOrderBO implements Serializable {
	private static final long serialVersionUID = -1840249277533507122L;
	/**
	 *开奖失败订单
	 */
	private List<FailOrderBO> fail;
	/**
	 *中奖订单
	 */
	private Integer winCount;

}
