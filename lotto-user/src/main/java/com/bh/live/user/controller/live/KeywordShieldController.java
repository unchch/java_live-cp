package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.KeywordShield;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.user.service.IHostUserService;
import com.bh.live.user.service.IKeywordShieldService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 房间发言关键词过滤表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/keyword-shield")
@Api(tags = "房间发言关键词过滤")
@Slf4j
public class KeywordShieldController extends BaseController {

    @Autowired
    private IKeywordShieldService keywordShieldService;

    @Autowired
    private IHostUserService hostUserService;


    @ApiOperation(value = "直播间关键词屏蔽列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = KeywordShield.class, code = 0, message = "直播间关键词屏蔽列表")})
    @GetMapping("/list")
    public Result list() {
        LiveUser user = getHeaderLiveUser();
        HostUserFormRes hostUser = hostUserService.getHostUserById(user == null ? 10007 : user.getId());
        return Result.success(keywordShieldService.shield(hostUser.getRoomId()));
    }

    @ApiOperation(value = "删除关键词", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "删除(单个和批量)关键词")})
    @GetMapping("/delKeyword")
    public Result delKeyword(@ApiParam("关键词id,多个id以逗号隔开") @RequestParam("ids") String ids) {
        boolean b = keywordShieldService.removeByIds(Arrays.asList(ids.split(",")));
        if (b) return Result.success();
        return Result.error(CodeMsg.E_500);
    }

    @ApiOperation(value = "添加关键词", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "添加关键词")})
    @GetMapping("/addKeyword")
    public Result addKeyword(@ApiParam("keyword：敏感词") @RequestParam("keyword") String keyword) {
        LiveUser user = getHeaderLiveUser();
        HostUserFormRes hostUser = hostUserService.getHostUserById(user == null ? 10007 : user.getId());
        KeywordShield keywordShield = new KeywordShield();
        keywordShield.setKeyword(keyword);
        keywordShield.setRoomId(hostUser.getRoomId());
        boolean b = keywordShieldService.save(keywordShield);
        if (b) return Result.success();
        return Result.error(CodeMsg.E_500);
    }
}

