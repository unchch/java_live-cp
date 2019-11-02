package com.bh.live.award.service.award.lottery.high.ssc.calculate;

import com.bh.live.award.service.award.lottery.high.WinInfo;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @desc 五星直选
 * @author WuLong
 * @date 2019-08-14
 */
@Service
public class FiveDirectCalculate implements ICalculate {
	private String prize = "五星直选";
	@Override
	public WinInfo simple(String content, String[] drawCode) {
		String[] codes = StringUtils.tokenizeToStringArray(content, "|;");
		int count = 0;
		int num = 0;
		for (int i = 1; i <= codes.length; i++) {
			String code = codes[i - 1];
			int y = i % 5;
			y = y == 0 ? 5 : y;
			if (code.equals(drawCode[y - 1])) {
				num++;
			}
			if (y == 5) {
				if (num == 5) {
					count++;
				}
				num = 0;
			}
		}
		return new WinInfo(count, prize);
	}

	@Override
	public WinInfo complex(String content, String[] drawCode) {
		String[] codes = StringUtils.tokenizeToStringArray(content, "|");
		int count = 0;
		for (int i = 0; i < codes.length; i++) {
			if(codes[i].indexOf(drawCode[i]) == -1){
				break;
			}
			if(i == 4){
				 count = 1;
			}
		}
		return new WinInfo(count, prize);
	}

	@Override
	public WinInfo sum(String content, String twoSum, String threeSum,String [] drawCode) {
		throw new ServiceRuntimeException(CodeMsg.E_80005);
	}

}
