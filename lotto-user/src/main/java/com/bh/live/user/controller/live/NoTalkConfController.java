package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.model.anchor.NoTalkConf;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.anchor.NoTalkConfReq;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.user.service.IHostUserService;
import com.bh.live.user.service.INoTalkConfService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.Map;

/**
 * <p>
 * 房间发言要求配置表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/no-talk-conf")
@Api(tags = "房间发言要求配置")
@Slf4j
public class NoTalkConfController extends BaseController {

    @Autowired
    private INoTalkConfService noTalkConfService;

    @Autowired
    private IHostUserService hostUserService;

    @ApiOperation(value = "修改或新增发言要求配置", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "修改或新增发言要求配置")})
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody NoTalkConfReq req) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        HostUserFormRes hostUser = hostUserService.getHostUserById(liveUser.getId());
        if (hostUser == null) return Result.error(CodeMsg.E_10004);
        NoTalkConf noTalkConf = new NoTalkConf();
        req.setRoomId(hostUser.getRoomId());
        BeanUtils.copyProperties(req, noTalkConf);
        try {
            boolean b = noTalkConfService.saveOrUpdate(noTalkConf);
            if (b) return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.error(CodeMsg.E_500);
    }

    @ApiOperation(value = "获取发言要求配置", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = NoTalkConfReq.class, code = 0, message = "获取发言要求配置")})
    @GetMapping("/getConf")
    public Result getConf() {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        HostUserFormRes hostUser = hostUserService.getHostUserById(liveUser.getId());
        if (hostUser == null) return Result.error(CodeMsg.E_10004);
        NoTalkConfReq noTalkConf = null;
        try {
            noTalkConf = noTalkConfService.getNoTalkConf(hostUser.getRoomId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success(noTalkConf);
    }

}

