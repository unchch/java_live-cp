package com.bh.live.controller.lottery;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.lottery.PlayOddsReq;
import com.bh.live.pojo.res.lottery.PlayOddsRes;
import com.bh.live.service.lottery.IPlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 玩法表 前端控制器
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/play")
@Api(tags = "彩种管理-玩法列表")
@Slf4j
public class PlayController extends BaseController {

    @Autowired
    private IPlayService playService;

    @ApiOperation(value = "玩法及赔率列表", response = PlayOddsRes.class)
    @GetMapping(value = "/odds/{seedNo}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result selectPlayOddsBySeedNo(@PathVariable("seedNo") @NotEmpty Integer seedNo) {
        log.info("updateOdds. req:{}", seedNo);
        return Result.success(playService.selectPlayOdds(seedNo));
    }

    @ApiOperation(value = "修改玩法赔率", response = PlayOddsReq.class)
    @PostMapping(value = "/odds/update", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Result updateOdds(@RequestBody Map<String, List<PlayOddsReq>> req) {
        log.info("updateOdds. req:{}", req);
        return playService.updatePlayOdds(req) ? Result.success() : Result.error(CodeMsg.E_500);
    }
}

