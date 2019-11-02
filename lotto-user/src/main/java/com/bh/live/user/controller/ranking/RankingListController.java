package com.bh.live.user.controller.ranking;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bh.live.common.result.Result;
import com.bh.live.pojo.res.rankingList.RankingListRes;
import com.bh.live.user.service.IRankingListService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 排行表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-07-31
 */
@RestController
@RequestMapping("/ranking/list")
@Api(tags = "排行榜")
public class RankingListController {
	@Resource
	private IRankingListService rankingListService;

	@ApiOperation(value = "根据周期来查询排行", response = RankingListRes.class)
	@RequestMapping(value = "/queryRankingList", method = RequestMethod.GET)
	public Result queryRankingListByPeriod(
			@ApiParam("周期类型 0天 1周 2月") @RequestParam(value = "rankPeriod", defaultValue = "0") Integer rankPeriod,
			@ApiParam("用户类型 0普通 1专家 2主播") @RequestParam(value = "userType", defaultValue = "0") Integer userType,
			@ApiParam("排行类型 0人气 1财富 2连胜  3胜率  4盈利率值") @RequestParam(value = "rankType", defaultValue = "0") Integer rankType,
			@ApiParam("查询条数") @RequestParam(value = "size", defaultValue = "10") int size) {
		return Result
				.success(rankingListService.queryRankingListByPeriod(rankPeriod, rankType, userType, size));
	}
}
