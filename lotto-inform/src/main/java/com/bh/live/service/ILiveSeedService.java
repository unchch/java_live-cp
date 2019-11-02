package com.bh.live.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.pojo.res.anchor.LiveSeedBannerRes;

/**
 * <p>
 * 直播彩种管理 服务类
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
public interface ILiveSeedService extends IService<LiveSeed> {
	
	/**
	 * 首页头部查询bannr
	 * @return
	 */
	public List<LiveSeedBannerRes> queryLiveSeedList();
}
