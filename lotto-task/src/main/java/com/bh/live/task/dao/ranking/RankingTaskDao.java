package com.bh.live.task.dao.ranking;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.rankingList.RankingList;
import com.bh.live.model.user.LiveUser;

/**
 * <p>
 * 排行表 Mapper 接口
 * </p>
 *
 * @author WW
 * @since 2019-08-01
 */
public interface RankingTaskDao extends BaseMapper<RankingList> {

	// 查询所有用户
	public List<LiveUser> queryAllLiveUser();

	// 查询人气和财富
	public RankingList queryPopularityAndTreasure(@Param("transType") String transType,
			@Param("userId") Integer userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	// 查询盈利和总赢率
	public RankingList queryWinrateAndProfitrate(@Param("userId") Integer userId,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 查询连续
	public List<Map> queryContinuous(@Param("userId") Integer userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	public int updateOrInsertRank(@Param("ranklist") List<RankingList> ranklist);

}
