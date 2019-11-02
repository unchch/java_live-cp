package com.bh.live.controller.configuration;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.model.configuration.LiveConfiguration;
import com.bh.live.service.configuration.ILiveConfigurationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 全局配置表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/live/configuration")
@Api(tags = "全局参数配置")
@SuppressWarnings("all")
public class LiveConfigurationController extends BaseController {

	@Resource
	private ILiveConfigurationService liveConfigurationService;

	@ApiOperation(value = "全局配置参数类型列表", response = LiveConfiguration.class)
	@GetMapping("/queryConfigType")
	public Result queryConfigTypeForKeyAndValue() {

		return Result.success(liveConfigurationService.queryConfigTypeForKeyAndValue());
	}

	@ApiOperation(value = "全局配置参数列表", response = LiveConfiguration.class)
	@RequestMapping(value = "/queryConfigList", method = RequestMethod.PUT)
	public Result queryUsableConfigByCondition(@RequestBody LiveConfiguration config) {

		return Result.success(liveConfigurationService.queryUsableConfigByCondition(config));
	}

	@ApiOperation(value = "批量添加全局配置参数", response = LiveConfiguration.class)
	@RequestMapping(value = "/addGlobalConfig", method = RequestMethod.PUT)
	public Result addGlobalConfig(@RequestBody List<LiveConfiguration> configList) {

		return Result.success(liveConfigurationService.addGlobalConfig(configList));
	}

	@ApiOperation(value = "批量修改全局配置参数", response = LiveConfiguration.class)
	@RequestMapping(value = "/updateGlobalConfig", method = RequestMethod.PUT)
	public Result updateGlobalConfig(@RequestBody List<LiveConfiguration> configList) {

		return Result.success(liveConfigurationService.addGlobalConfig(configList));
	}
}
