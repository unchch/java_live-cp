package com.bh.live.controller.user;

import cn.hutool.core.lang.Assert;
import com.bh.live.common.constant.JxlsTemplateConst;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.jxls.JxlsExcelUtil;
import com.bh.live.model.cms.UserStatisticsCMS;
import com.bh.live.pojo.req.user.LiveUserFullReq;
import com.bh.live.pojo.res.user.LiveUserFullRes;
import com.bh.live.pojo.res.user.UserStatisticsRes;
import com.bh.live.service.user.ILiveUserService;
import com.bh.live.utils.PropUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 直播用户表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/live/user")
@Api(tags = "直播用户")
@SuppressWarnings("all")
public class LiveUserController extends BaseController {

	@Resource
	private ILiveUserService liveUserService;

	@ApiOperation(value = "直播用户列表", response = LiveUserFullRes.class)
	@RequestMapping(value = "/queryLiveUserPage", method = RequestMethod.PUT)
	public Result queryLiveUserPage(@RequestBody(required = false) LiveUserFullReq liveUser) {

		return Result.success(liveUserService.queryLiveUserPage(liveUser));
	}

	@ApiOperation(value = "根据id查询直播用户信息", response = UserStatisticsRes.class)
	@GetMapping("/liveUserInfo")
	public Result queryLiveUserInfoByID(
			@ApiParam("用户id") @RequestParam(value = "user_id", required = true) Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		return Result.success(liveUserService.queryLiveUserAllInfoByID(userId));
	}

	@ApiOperation(value = "更新直播用户", response = Boolean.class)
	@RequestMapping(value = "/updateLiveUser", method = RequestMethod.PUT)
	public Result updateLiveUserInfo(@RequestBody LiveUserFullReq liveUser) {
		Assert.notNull(liveUser.getId(), "用户id不能为空");
		return Result.success(liveUserService.updateLiveUserInfo(liveUser));
	}

	@ApiOperation(value = "用户的解除或禁止行为操作  0:禁止登录 1:禁止发布竞猜方案 2:禁止聊天室发言", response = Boolean.class)
	@GetMapping("/isProhibit")
	public Result isProhibit(
			@ApiParam("用户id") @RequestParam(value = "user_id", required = true) Integer userId,
			@ApiParam("操作类型：0:禁止登录 1:禁止发布竞猜方案 2:禁止聊天室发言") @RequestParam(value = "prohibit_type", required = true) String prohibitType) {
		Assert.notNull(userId, "用户id不能为空");
		return Result.success(liveUserService.isProhibit(userId, prohibitType));
	}

	@ApiOperation(value = "强制用户退出登录", response = Boolean.class)
	@GetMapping("/forceToExit")
	public Result forceToExit(
			@ApiParam("用户id") @RequestParam(value = "user_id", required = true) Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		return Result.success(liveUserService.forceToExit(userId));
	}

	@ApiOperation(value = "解除或禁止用户", response = Boolean.class)
	@GetMapping("/isUsableLiveUser")
	public Result isProhibitLiveUser(
			@ApiParam("用户id") @RequestParam(value = "user_id", required = true) Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		return Result.success(liveUserService.isProhibitLiveUser(userId));
	}
	
	@ApiOperation(value = "查询用户统计信息", response = UserStatisticsCMS.class)
	@GetMapping("/queryUserStatistics")
	public Result queryUserStatistics() {
		return Result.success(liveUserService.queryUserStatistics());
	}

	@ApiOperation(value = "重置密码", response = Boolean.class)
	@GetMapping("/resetUserpassword")
	public Result resetUserpassword(@RequestParam(value = "userId", required = true)  Integer userId) {

		return Result.success(liveUserService.resetUserpassword(userId));
	}

	@GetMapping("/export")
	@ApiOperation(value = "导出用户列表")
	public void export(LiveUserFullReq liveUser, HttpServletResponse response) {
		new JxlsExcelUtil<>(PropUtil.fileExportUrl, liveUserService.queryUserTransExcel(liveUser), JxlsTemplateConst.LIVE_USER_TEMPLATE, response);
	}
}
