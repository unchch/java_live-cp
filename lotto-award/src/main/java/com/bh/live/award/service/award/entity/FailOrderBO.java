package com.bh.live.award.service.award.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *@author WuLong
 *@date 2019/8/5 20:08
 *@description 失败信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailOrderBO implements Serializable {
	/**
	 *开奖订单号
	 */
	private String orderCode;
	/**
	 *开奖错误信息
	 */
	private String message;
}
