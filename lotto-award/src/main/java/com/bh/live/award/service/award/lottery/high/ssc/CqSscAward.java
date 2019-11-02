package com.bh.live.award.service.award.lottery.high.ssc;

import com.bh.live.award.service.award.lottery.high.ssc.calculate.*;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @desc 重庆时时彩开奖
 * @author WuLong
 * @date 2017年7月12日

 * @version 1.0
 */
@Service
public class CqSscAward extends AbstractSscAward {
	
	@Autowired
    private OneCalculate oneCalculate;
	@Autowired
	private TwoDirectCalculate twoDirectCalculate;
	@Autowired
	private ThreeDirectCalculate threeDirectCalculate;
	@Autowired
	private FiveDirectCalculate fiveDirectCalculate;
	@Autowired
	private SizeCalculate sizeCalculate;
	@Autowired
	private TwoGroupCalculate twoGroupCalculate;
	@Autowired
	private ThreeGroupSixCalculate threeGroupSixCalculate;
	@Autowired
	private ThreeGroupThreeCalculate threeGroupThreeCalculate;
	@Autowired
	private FiveAllCalculate fiveAllCalculate;
	
	@Override
	protected ICalculate getCalculate(int lotteryChildCode) {
		switch (lotteryChildCode) {
		case 20101:// 重庆时时彩五星直选
			return fiveDirectCalculate;
		case 20102:// 重庆时时彩五星通选
			return fiveAllCalculate;
		case 20103:// 重庆时时彩三星直选
			return threeDirectCalculate;
		case 20104:// 重庆时时彩三星组三
			return threeGroupThreeCalculate;
		case 20105:// 重庆时时彩三星组六
			return threeGroupSixCalculate;
		case 20106:// 重庆时时彩二星直选
			return twoDirectCalculate;
		case 20107:// 重庆时时彩二星组选
			return twoGroupCalculate;
		case 20108:// 重庆时时彩一星
			return oneCalculate;
		case 20109:// 重庆时时彩大小单双
			return sizeCalculate;
		default:
			throw new ServiceRuntimeException(CodeMsg.E_80006);
		}
	}

	@Override
	public void getResultOdds() {

	}
}
