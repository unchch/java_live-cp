package com.bh.live.dao;


import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.pojo.res.inform.HongKongMisssRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface HongKongLotteryDao {
	List<HongKongMisssRes> queryTwoDateMiss(@Param("param") Integer param);

	List<HongKongMisssRes> queryNowYearData();

	List<StatHistoryDraw> queryHistoricalPeriod(@Param("expect") int expect);
}
