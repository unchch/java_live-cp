package com.bh.live.service;

import java.util.Map;

public interface HongKongLotteryService {
	Map<String,Object> queryTemaMissing(int total);

	Map<String, Object> queryTwoDateMissing();

	Map<String, Object> queryTmLastNumberMissing();

	Map<String, Object> queryZodiacCyclesMiss();

	Map<String, Object> queryNumberSegmentMiss();

	Map<String, Object> queryZmaMissing(int total);

	Map<String, Object> queryZodiacTemaMissing();

	Map<String, Object> queryZodiacZmaMissing();

	Map<String, Object> queryZodiacMissing();

	Map<String, Object> queryBoseMissing();

	Map<String, Object> queryHistoricalPeriod();

	Map<String, Object> queryHistoryMissing(String date, String type);
}
