package com.bh.live.service;


import com.bh.live.common.result.Result;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public interface DewdropService {
	
	/**
	 * 号码前后露珠
	 * */
	Map<String, Object> queryDewdropAround(String param, String varietyType);


	Result queryInfoDome(String a,String b);
	/**
	 * 冠亚和露珠
	 * */
	Map<String, Object> queryDewdropChampion(String param, String varietyType);

	/**
	 * 综合露珠
	 * */
	Map<String, Object> queryComprehensiveDewdrop(String param, String varietyType, String type);

	//单双大小
	Map<String, Object> querySingleDoubleDewdrop(String param, String varietyType, String type);

	Map<String, Object> queryDragonTigerDewdrop(String openTime, String varietyType);

	/**
	 * 综合露珠设置形态
	 * */
	int querySetForm(String openTime, int ball, String type, String match, String varietyType);

	/**
	 * 总和露珠
	 * */
	Object queryTotalDewdrop(String openTime, String varietyType);

	/**
	 * 号码露珠
	 * */
	Map<String,Object> queryNumberDewdrop(String openTime, String varietyType);

	/**
	 * 奇偶露珠
	 * */
	Map<String,Object> queryOddEveDewdrop(String openTime, String varietyType);

	/**
	 * 上下盘
	 * */
	Map<String,Object> queryUpDownDewdrop(String openTime, String varietyType);

	/**
	 * 尾数露珠
	 * */
	Map<String,Object> queryLastNumberDewdrop(String openTime, String varietyType);

	/**
	 * 合数单双
	 * */
	Map<String, Object> querySumNumberDewdrop(String param, String varietyType);

	/**
	 * 中发白
	 * */
	Map<String, Object> queryMiddleHairDewdrop(String openTime, String varietyType);

	Map<String,Object> queryNorthSouthDewdrop(String openTime, String varietyType);

	/**
	 * 香港彩
	 * */
	Map<String,Object> queryHongKongColour(String code);

	/**
	 * 正码龙虎
	 * */
	Map<String,Object> queryCodeDragonTiger();
}
