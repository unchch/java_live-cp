package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.configuration.LiveConfiguration;
import com.bh.live.model.user.Attention;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.pojo.res.anchor.HostUserHomePageRes;
import com.bh.live.pojo.res.anchor.HostUserRecruitReq;
import com.bh.live.user.service.IHostUserService;
import com.bh.live.user.service.ILiveConfigurationService;
import com.bh.live.user.service.ILiveSeedService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 主播表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@RestController
@RequestMapping("/hostUser")
@Slf4j
@Api(tags = "主播前端管理")
public class HostUserController extends BaseController {

    @Autowired
    private IHostUserService hostUserService;

    @Autowired
    private ILiveSeedService liveSeedService;

    @Autowired
    private ILiveConfigurationService confService;

    /**
     * 主播详情
     *
     * @return
     */
    @ApiOperation(value = "主播详情", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = HostUserFormRes.class, code = 0, message = "主播详情")})
    @GetMapping("/detail/{userId}")
    public Result detail(@ApiParam("路径参数用户id") @PathVariable("userId") Integer userId) {
        try {
            //主播基本信息
            HostUserFormRes hostUser = hostUserService.getHostUserById(userId);
            return Result.success(hostUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
    }

    /**
     * 主播修改
     *
     * @return
     */
    @ApiOperation(value = "主播修改", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "主播修改")})
    @PostMapping("/update")
    public Result update(@ApiParam("修改的主播信息") @RequestBody HostUserReq req) {
        try {
            hostUserService.updateHostUser(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @ApiOperation(value = "获取用户电话号码", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "获取用户电话号码")})
    @GetMapping("/getPhone")
    public Result getPhone() {
        try {
            LiveUser liveUser = getHeaderLiveUser();
            return Result.success(liveUser.getMobile());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @ApiOperation(value = "主播招募", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "主播招募")})
    @PostMapping("/recruit")
    public Result recruit(@ApiParam("form表单提交") @RequestBody HostUserRecruitReq formRes) {
        try {
            return hostUserService.registerHostUser(formRes);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error(CodeMsg.E_10005);
            }
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @ApiOperation(value = "玩彩年限下拉接口", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "玩彩年限下拉接口")})
    @GetMapping("/ageLimit")
    public Result ageLimit() {
        try {
            LiveConfiguration configuration = new LiveConfiguration();
            configuration.setTypeValue("play_limit");
            List<LiveConfiguration> confs = confService.queryUsableConfigByCondition(configuration);
            return Result.success(confs);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
    }

    @ApiOperation(value = "直播彩种接口", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "直播彩种接口")})
    @GetMapping("/seedList")
    public Result seedList() {
        try {
            return Result.success(liveSeedService.getCategoryLiveSeed());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
    }

    /**
     * 主播个人主页（用户查看）
     *
     * @return
     */
    @ApiOperation(value = "主播个人主页", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = HostUserHomePageRes.class, code = 0, message = "主播个人主页")})
    @GetMapping("/homePage/{userId}")
    public Result homePage(@ApiParam("路径参数用户id") @PathVariable("userId") Integer userId) {
        LiveUser liveUser = getHeaderLiveUser();
        HostUserHomePageRes homePageRes = null;
        try {
            //主播基本信息
            homePageRes = hostUserService.selectUserHomePageDetail(userId);
            if (liveUser == null) {
                homePageRes.setIsAttent(0);
            } else {
                Attention attention = hostUserService.getIsAttention(userId, liveUser.getId());
                if (attention == null) {
                    homePageRes.setIsAttent(0);
                } else {
                    homePageRes.setIsAttent(attention.getIsAttent());
                }
            }
        } catch (Exception e) {
            log.error("查询主播信息异常：{},{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
        return Result.success(homePageRes);
    }
}

