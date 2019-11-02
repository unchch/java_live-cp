package com.bh.live.award.service.award.lottery.high.ssc.calculate;

import com.bh.live.award.service.award.lottery.high.WinInfo;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import org.springframework.stereotype.Service;

/**
 * @desc 一星
 * @author WuLong
 * @date 2019-08-14
 */
@Service
public class OneCalculate implements ICalculate {
	private String prize = "一星";
	@Override
	public WinInfo simple(String content, String[] drawCode) {
		int num = 0;
		char code = drawCode[4].charAt(0);
		for (byte b : content.getBytes()) {
			if(b == code){
				num++;
			}
		}
		return new WinInfo(num, prize);
	}

	@Override
	public WinInfo complex(String content, String[] drawCode) {
		throw new ServiceRuntimeException(CodeMsg.E_80005);
	}

	@Override
	public WinInfo sum(String content, String twoSum, String threeSum,String [] drawCode) {
		throw new ServiceRuntimeException(CodeMsg.E_80005);
	}

}
