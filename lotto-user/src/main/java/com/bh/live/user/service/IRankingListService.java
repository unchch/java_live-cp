package com.bh.live.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.rankingList.RankingList;
import com.bh.live.pojo.res.rankingList.RankingListRes;

/**
 * <p>
 * 排行表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-07-31
 */
public interface IRankingListService extends IService<RankingList> {

	/**
	 * 根据周期来查询排行
	 * 
	 * @param rankPeriod
	 * @param size
	 * @return
	 */
	public List<RankingListRes> queryRankingListByPeriod(Integer rankPeriod, Integer rankType,
			Integer userType, int size);
}
