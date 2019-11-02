package com.bh.live.award.service.award.lottery.high.ssc.calculate;

import com.bh.live.award.service.award.lottery.high.WinInfo;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @desc 三星组六
 * @author WuLong
 * @date 2019-08-14
 */
@Service
public class ThreeGroupSixCalculate implements ICalculate {
	private String prize = "三星组六";
	@Override
	public WinInfo simple(String content, String[] drawCode) {
		String[] codes = StringUtils.tokenizeToStringArray(content, ",;");
		int num = 0, count = 0;
		for (int i = 1; i <= codes.length; i++) {
			String code = codes[i - 1];
			if (code.equals(drawCode[2]) || code.equals(drawCode[3]) || code.equals(drawCode[4])) {
				num++;
			}
			if (i % 3 == 0) {
				if (num == 3) {
					count++;
				}
				num = 0;
			}
		}
		return new WinInfo(count, prize);
	}

	@Override
	public WinInfo complex(String content, String[] drawCode) {
		int count = 0;
		if(check(drawCode)){
			String[] codes = content.split(",");
			int num = 0;
			for (int i = 2; i < drawCode.length; i++) {
				for (int j = 0; j < codes.length; j++) {
					if (codes[j].equals(drawCode[i])) {
						num++;
						break;
					}
				}
			}
		   count = num == 3 ? 1 : 0;
		}
		return new WinInfo(count, prize);
	}

	@Override
	public WinInfo sum(String content, String twoSum, String threeSum,String [] drawCode) {
		throw new ServiceRuntimeException(CodeMsg.E_80005);
	}
	/**
	 * 验证是否是组六
	 * @author WuLong
	 * @Version 1.0
	 * @CreatDate 2017年10月13日 下午2:26:38
	 * @param drawCode
	 * @return
	 */
	public boolean check(String[] drawCode){
		String drawCode3 = drawCode[2];
		String drawCode4 = drawCode[3];
		String drawCode5 = drawCode[4];
		if(!drawCode3.equals(drawCode4)
				&& !drawCode4.equals(drawCode5)
				&& !drawCode3.equals(drawCode5)){
			return true;
		}
		return false;
	}
	
}
