package com.bh.live.controller.lottery;


import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.JsonUtil;
import com.bh.live.pojo.req.lottery.SeedReq;
import com.bh.live.pojo.req.lottery.SeedSettingsReq;
import com.bh.live.pojo.req.lottery.SeedUpdateReq;
import com.bh.live.pojo.res.lottery.SeedPageRes;
import com.bh.live.service.lottery.ISeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 彩种表 前端控制器
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/seed")
@Api(tags = "彩种管理-彩种")
@Slf4j
public class SeedController extends BaseController {

    @Autowired
    private ISeedService seedService;

    @ApiOperation(value = "列表", response = SeedPageRes.class)
    @PostMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result selectSeedPage(SeedReq req) {
        log.info("selectSeedPage. req:{}", JsonUtil.obj2json(req));
        return Result.success(seedService.selectSeedPage(req));
    }

    @ApiOperation(value = "查询彩种及赔率信息", response = SeedPageRes.class)
    @GetMapping(value = "/query/{seedNo}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result query(@PathVariable("seedNo") @NotEmpty Integer seedNo) {
        return Result.success(seedService.query(seedNo));
    }

    @ApiOperation(value = "修改", response = SeedPageRes.class)
    @PostMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result update(@RequestBody SeedUpdateReq req) {
        try {
            req.setUpdateBy(LotteryConstants.loginName);
            if (seedService.update(req)) {
                return Result.success();
            }
        } catch (Exception e) {
            log.error("[exception] seedSettings. cause:{} message:{}", e.getCause(), e.getMessage());
        }
        return Result.error(CodeMsg.E_500);
    }

    @ApiOperation(value = "设置默认官方玩法or信用玩法", response = Boolean.class)
    @GetMapping(value = "/defaultPlay/{seedNo}/{playMode}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result defaultPlay(@PathVariable("seedNo") @NotEmpty Integer seedNo,
                              @PathVariable("playMode") @NotEmpty Integer playMode) {
        log.info("defaultPlay. req:{} seedNo:{} playMode:{}", seedNo, playMode);
        return seedService.defaultPlay(seedNo, playMode) ? Result.success() : Result.error(CodeMsg.E_500);
    }

    @ApiOperation(value = "显示or隐藏彩种", response = Boolean.class)
    @GetMapping(value = "/display/{seedNo}/{showAble}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result display(@PathVariable("seedNo") @NotEmpty Integer seedNo,
                          @PathVariable("showAble") @NotEmpty Integer showAble) {
        log.info("display. req: seedNo{} showAble:{}", seedNo, showAble);
        return seedService.display(seedNo, showAble) ? Result.success() : Result.error(CodeMsg.E_500);
    }

    @ApiOperation(value = "查询彩种玩法配置", response = Boolean.class)
    @GetMapping(value = "/settings/{seedNo}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result querySettings(@PathVariable("seedNo") @NotEmpty Integer seedNo) {
        return Result.success(seedService.querySettings(seedNo));
    }

    @ApiOperation(value = "设置彩种玩法配置", response = Boolean.class)
    @PostMapping(value = "/settings", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result settings(@RequestBody List<SeedSettingsReq> req) {
        log.info("settings. req:{}", JsonUtil.obj2json(req));
        try {
            if (seedService.settings(req)) {
                return Result.success();
            }
        } catch (Exception e) {
            log.error("[exception] settings. cause:{} message:{}", e.getCause(), e.getMessage());
        }
        return Result.error(CodeMsg.E_500);
    }
}

