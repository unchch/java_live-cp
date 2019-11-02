package com.bh.live.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.pojo.res.anchor.LiveSeedBannerRes;
import com.bh.live.service.ILiveSeedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 直播彩种管理 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/live/seed")
@Api(tags = "首页banner")
public class LiveSeedController {

	@Resource
	private ILiveSeedService liveSeedService;

	@ApiOperation(value = "首页头部banner", response = LiveSeedBannerRes.class)
	@GetMapping("/queryLiveSeedList")
	public Result queryLiveSeedList() {
		
		return Result.success(liveSeedService.queryLiveSeedList());
	}
}
