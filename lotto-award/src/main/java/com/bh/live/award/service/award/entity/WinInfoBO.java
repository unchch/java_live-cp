package com.bh.live.award.service.award.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *@author WuLong
 *@date 2019/8/5 20:10
 *@description 中奖信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinInfoBO implements Serializable {
	private static final long serialVersionUID = 6598691656872270542L;
	/**
	 *中奖金额
	 */
	private Integer money;
	/**
	 * 中奖信息
	 */
	private String detail;
}
