package com.bh.live.task.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bh.live.task.service.ranking.RankingTaskService;
import com.bh.live.task.util.DateUtis;

/**
 * <p>
 * 排行表采集器
 * </p>
 *
 * @author WW
 * @since 2019-08-01
 */
@Component
public class RankingListTask {

	@Resource
	private RankingTaskService rankingListService;

	// 周期 0天 1周 2月 更新时间

	// 实时统计
	@Scheduled(cron = "0 0 12 * * ? ")
	public void realtimeCollectRankingList() {
		rankingListService.collectRankingList(0, DateUtis.getToday(), DateUtis.getCurrent());
	}

	// 每周统计
	@Scheduled(cron = "0 15 10 ? * MON")
	public void weeklyCollectRankingList() {
		rankingListService.collectRankingList(1, DateUtis.getWeekStart(), DateUtis.getWeekEnd());
	}

	// 每月统计
	@Scheduled(cron = "0 15 9 10 * ?")
	public void monthlyCollectRankingList() {
		rankingListService.collectRankingList(2, DateUtis.getMonthStart(), DateUtis.getMonthEnd());
	}

}
