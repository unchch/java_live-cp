package com.bh.live.task.service.impl.ranking;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.rankingList.RankingList;
import com.bh.live.model.user.LiveUser;
import com.bh.live.task.dao.ranking.RankingTaskDao;
import com.bh.live.task.service.ranking.RankingTaskService;

/**
 * <p>
 * 排行表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-08-01
 */
@Service
public class RankingTaskServiceImpl extends ServiceImpl<RankingTaskDao, RankingList>
		implements RankingTaskService {

	@Resource
	private RankingTaskDao rankingListTaskDao;

	// 人气值 财富值 胜率值 连胜值 盈利率值 周期 0天 1周 2月 3总 更新时间
	// 交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除 9:主播提成
	@Override
	public void collectRankingList(Integer period, String startTime, String endTime) {
		List<RankingList> list = new LinkedList<RankingList>();
		rankingListTaskDao.queryAllLiveUser().forEach(liveuser -> {
			RankingList ranking = new RankingList();
			ranking.setUserId(liveuser.getId());// 用户id
			ranking.setUserType(selectUserType(liveuser));// 用户类型
			ranking.setIsUsable(liveuser.getIsUsable());// 是否可用
			ranking.setRankPeriod(period);// 周期
			RankingList pt = updatePopularityAndTreasure(liveuser.getId(),
					changeTransTypeByUserType(selectUserType(liveuser)), startTime, endTime);
			ranking.setPopularityValue(pt.getPopularityValue());
			ranking.setTreasureValue(pt.getTreasureValue());
			RankingList wp = updateWinrateAndProfitrate(liveuser.getId(), startTime, endTime);
			ranking.setWinrateValue(wp.getWinrateValue());
			ranking.setProfitrateValue(wp.getProfitrateValue());
			ranking.setWingingValue(queryContinuousSize(liveuser.getId(), startTime, endTime));// 连赢
			ranking.setNumberOf(queryContinuousNumberOf(liveuser.getId(), startTime, endTime));
			list.add(ranking);
		});
		rankingListTaskDao.updateOrInsertRank(list);
	}

	// 用户类型 0普通 1专家 2主播
	public Integer selectUserType(LiveUser liveuser) {
		if (liveuser != null && liveuser.getIsExpert() == 1) {// 是否是专家 0否 1是
			return 1;
		} else if (liveuser != null && liveuser.getIsAnchor() == 1) {// 是否是主播 0否 1是
			return 2;
		} else {
			return 0;
		}
	}

	// 交易类型转换
	public String changeTransTypeByUserType(Integer userType) {
		// 用户类型 0普通 1专家 2主播
		// 交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除 9:主播提成
		switch (userType) {
		case 1:
			return "3";
		case 2:
			return "9";
		default:
			return "3";
		}
	}

	// 更新人气和财富
	public RankingList updatePopularityAndTreasure(Integer userId, String transType, String startTime,
			String endTime) {
		return rankingListTaskDao.queryPopularityAndTreasure(transType, userId, startTime, endTime);
	}

	// 更新人气和财富
	public RankingList updateWinrateAndProfitrate(Integer userId, String startTime, String endTime) {
		return rankingListTaskDao.queryWinrateAndProfitrate(userId, startTime, endTime);
	}

	// 时间段内最大连赢
	public int queryContinuousSize(Integer userId, String startTime, String endTime) {
		int count = 0;
		List<Map> list = rankingListTaskDao.queryContinuous(userId, startTime, endTime);
		for (Iterator<Map> iterator = list.iterator(); iterator.hasNext();) {
			Map map = iterator.next();
			Boolean awardState = (Boolean) map.get("award_state");// 中奖输赢状态(0:默认，1:赢，2:输，3:和)
			if (awardState) {
				count++;
				continue;
			}
			return count;
		}
		return count;
	}

	// 时间段内场数
	public int queryContinuousNumberOf(Integer userId, String startTime, String endTime) {

		return rankingListTaskDao.queryContinuous(userId, startTime, endTime).size();
	}


}
