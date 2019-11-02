package com.bh.live.task.service.ranking;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.rankingList.RankingList;

/**
 * <p>
 * 排行表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-08-01
 */
public interface RankingTaskService extends IService<RankingList> {

	/**
	 * 采集排行信息
	 * 
	 * @param period
	 */
	public void collectRankingList(Integer period, String startTime, String endTime);

}
