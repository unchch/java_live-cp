package com.bh.live.dao;

import com.bh.live.pojo.req.inform.DewDropReq;
import com.bh.live.pojo.res.inform.DragonRes;
import com.bh.live.pojo.res.inform.TwoDataStatisticsRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TwoDataStatisticsDao {

	/**
	 * 查询两面数据统计
	 * */
	List<TwoDataStatisticsRes> queryTwoDataStatistics(@Param("number") String number, @Param("varietyType") String varietyType, @Param("time") int time);
	/**
	 * 号码规律
	 * number 号码位置 字段名
	 * param 号码 int
	 * */
	List<Integer> queryNumberLaw(@Param("number") String number, @Param("param") Integer param);
	
	/**
	 * 遗漏大数据
	 * */
	List<DragonRes> queryMissingBigData(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("index") int index);
	
	List<TwoDataStatisticsRes> queryMissingBigOneData(@Param("dto") DewDropReq dto);
	


}
