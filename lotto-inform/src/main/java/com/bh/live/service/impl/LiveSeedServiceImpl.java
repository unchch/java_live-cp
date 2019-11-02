package com.bh.live.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.LiveSeedDao;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.pojo.res.anchor.LiveSeedBannerRes;
import com.bh.live.service.ILiveSeedService;

/**
 * <p>
 * 直播彩种管理 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
@Service
public class LiveSeedServiceImpl extends ServiceImpl<LiveSeedDao, LiveSeed> implements ILiveSeedService {

	@Override
	public List<LiveSeedBannerRes> queryLiveSeedList() {

		return super.list(new QueryWrapper<LiveSeed>().lambda().eq(LiveSeed::getIsDel, 0)
				.eq(LiveSeed::getRecommendIndex, 1).eq(LiveSeed::getStatus, 1).orderByDesc(LiveSeed::getSort))
						.stream().map(e -> new LiveSeedBannerRes(e.getLsId(), e.getSeedNo(), e.getLiveCount(),
								e.getCover()))
						.collect(Collectors.toList());
	}

}
