package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.BarrageConf;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.user.service.IBarrageConfService;
import com.bh.live.user.service.IHostUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 房间弹幕功能配置表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/barrage-conf")
@Slf4j
@Api(tags = "弹屏配置")
public class BarrageConfController extends BaseController {
    @Autowired
    private IHostUserService hostUserService;

    @Autowired
    private IBarrageConfService barrageConfService;

    @ApiOperation(value = "修改或新增房间弹幕功能配置", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "修改或新增房间弹幕功能配置")})
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody BarrageConf conf) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        HostUserFormRes hostUser = hostUserService.getHostUserById(liveUser.getId());
        if (hostUser == null) return Result.error(CodeMsg.E_10004);
        conf.setRoomId(hostUser.getRoomId());
        boolean b = false;
        try {
            b = barrageConfService.saveOrUpdate(conf);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        if (b) return Result.success();
        return Result.error(CodeMsg.E_500);
    }

    @ApiOperation(value = "获取发言要求配置", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = BarrageConf.class, code = 0, message = "获取发言要求配置")})
    @GetMapping("/getConf")
    public Result getConf() {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        HostUserFormRes hostUser = hostUserService.getHostUserById(liveUser.getId());
        if (hostUser == null) return Result.error(CodeMsg.E_10004);
        BarrageConf conf = null;
        try {
            conf = barrageConfService.getBarrageConfByRoomId(hostUser.getRoomId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        if (conf != null) return Result.success(conf);
        return Result.success(new BarrageConf());
    }
}

