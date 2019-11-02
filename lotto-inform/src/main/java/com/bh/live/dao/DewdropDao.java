package com.bh.live.dao;

import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.pojo.req.inform.DewDropReq;
import com.bh.live.pojo.res.inform.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DewdropDao  {

	String queryInfoDome();
	/**
	 * 查询pk10的历史数据
	 * */
	List<StatHistoryDraw> queryStatHistoryPK10(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("varietyType")String varietyType);

	/**
	 * 今日龙虎累计数量
	 * */
	DragonTigerNumRes queryTodayStatistics(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("varietyType")String varietyType);

	/**
	 *
	 * 单双大小龙胡露珠统计
	 * @param dto
	 *
	 * */
	List<DewdropRes> querySmallBig(@Param("dto") DewDropReq dto);

	List<StatHistoryDraw> queryExpectLimt(@Param("limit")int limit,@Param("varietyType")String varietyType);

	List<DewDropReq> queryStatHistoryPK10DESC(@Param("dto")DewDropReq vo);

	//今日累计数量
	CumulativeQuantityRes queryCumulativeQuantity(@Param("dto")DewDropReq dto);

	/**
	 * 特殊号码的大小单双
	 * */
	List<DewdropRes> queryCrownSub(@Param("dto")DewDropReq dto);

	/**
	 * 中发白
	 * */
	List<MiddleHairRes> queryMiddleHairDewdrop(@Param("dto")DewDropReq dto);
	MiddleHairRes queryMiddleHairNumber(@Param("dto")DewDropReq dto);

	/**
	 * 东西南北
	 * */
	List<NorthSouthDewdropRes> queryNorthSouthDewdrop(@Param("dto")DewDropReq dto);
	NorthSouthDewdropRes queryNorthSouthNumber(@Param("dto")DewDropReq dto);

	/**
	 * 香港彩
	 * @param greenList
	 * @param blueList
	 * @param redList
	 * @param wordList
	 * @param sexList
	 * @param poultryList
	 * */
	List<HongKongColourRes> queryHongKongColour(@Param("dto")DewDropReq dto, @Param("poultryList") List<Integer> poultryList, @Param("sexList")List<Integer> sexList,
												@Param("wordList")List<Integer> wordList, @Param("redList")List<Integer> redList, @Param("blueList")List<Integer> blueList, @Param("greenList")List<Integer> greenList);

	/**
	 * 正码龙虎
	 * */
	List<CodeDragonTigerRes> queryCodeDragonTiger();

}
