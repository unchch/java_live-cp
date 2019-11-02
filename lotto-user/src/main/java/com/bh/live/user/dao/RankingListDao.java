package com.bh.live.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.rankingList.RankingList;
import com.bh.live.pojo.res.rankingList.RankingListRes;

/**
 * <p>
 * 排行表 Mapper 接口
 * </p>
 *
 * @author WW
 * @since 2019-07-31
 */
public interface RankingListDao extends BaseMapper<RankingList> {

	public Integer queryRankingCountByPeriod(@Param("rankPeriod") Integer rankPeriod,
			@Param("userType") Integer userType);// 查询排行总数

	public List<RankingListRes> queryRankingListByPeriod(@Param("rankPeriod") Integer rankPeriod,
			@Param("rankType") Integer rankType, @Param("userType") Integer userType, @Param("size") Integer size);// 查询排行

}
