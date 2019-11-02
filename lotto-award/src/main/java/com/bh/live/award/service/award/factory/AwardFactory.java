package com.bh.live.award.service.award.factory;

import com.bh.live.award.service.award.AbstractAward;
import com.bh.live.award.service.award.lottery.high.ssc.CqSscAward;
import com.bh.live.award.service.award.lottery.high.pkten.BeijingCarAward;
import com.bh.live.award.service.award.lottery.high.pkten.LuckyAirshipAward;
import com.bh.live.common.enums.lottery.LotteryEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@author WuLong
 *@date 2019/8/5 21:06
 *@description 获取开奖处理类
 */
@Service
public class AwardFactory {

	@Autowired
	private CqSscAward cqSscAward;
	@Autowired
	private LuckyAirshipAward luckyAirshipAward;
	@Autowired
	private BeijingCarAward beijingCarAward;


	/**
	 *@description 根据彩种获取
	 *@author WuLong
	 *@date 2019/8/5 19:59
	 *@param lotteryCode
	 *@return
	 *@exception
	 */
	public AbstractAward getAward(Integer lotteryCode){
        LotteryEnum.LottoSeedNoEnum lottoSeed = LotteryEnum.LottoSeedNoEnum.getLottoSeed(lotteryCode);
        AbstractAward award = null;
		switch (lottoSeed) {
		case CQ_SSC:
			award = cqSscAward;
			break;
		case LUCKY_AIRSHIP:
			award = luckyAirshipAward;
            break;
		case BJ_CAR:
			award = beijingCarAward;
			break;
		case HONG_KONG:
			break;
		default:
			break;
		}
		if(award == null){
			throw new ServiceRuntimeException(CodeMsg.E_80003);
		}
		return award.clone();
	}
}
