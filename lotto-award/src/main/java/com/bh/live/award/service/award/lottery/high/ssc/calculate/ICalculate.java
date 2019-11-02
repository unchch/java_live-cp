package com.bh.live.award.service.award.lottery.high.ssc.calculate;


import com.bh.live.award.service.award.lottery.high.WinInfo;

public interface ICalculate {

	/**
	 * 单式计算规则
	 * @author WuLong
	 * @date 2019-08-14
	 * @param content 投注类容
	 * @param drawCode 开奖号码
	 * @return 中奖注数
	 */
	WinInfo simple(String content, String[] drawCode);
	/**
	 * 复试计算规则
	 * @author WuLong
	 * @date 2019-08-14
	 * @param content 投注类容
	 * @param drawCode 开奖号码
	 * @return 中奖注数
	 */
	WinInfo complex(String content, String[] drawCode);
	/**
	 * @author WuLong
	 * @date 2019-08-14
	 * @param content
	 * @return
	 */
	WinInfo sum(String content, String twoSum, String threeSum, String[] drawCode);
}
