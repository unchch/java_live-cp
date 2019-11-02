package com.bh.live.service;


import java.util.List;
import java.util.Map;

public interface TwoDataStatisticsService {
	
	/**
	 * 两面数据统计
	 * */
	List<Object> queryTwoDataStatistics(int ball, String varietyType);

	/**
	 * 号码规律
	 * @param expect 
	 * @param varietyType 
	 * */
	Map<String, Object> queryNumberLaw(int number, int expect, String varietyType);

	/**
	 * 遗漏大数据
	 * @paramopenTime  天数
	 * */
	Map<String, Object> queryMissingBigData(String dateType, int index, String varietyType);

}
