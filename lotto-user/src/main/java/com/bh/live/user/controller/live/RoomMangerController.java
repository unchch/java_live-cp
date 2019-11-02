package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.anchor.HostRoomReq;
import com.bh.live.pojo.res.anchor.HostRoomRes;
import com.bh.live.pojo.res.user.RoomMangerRes;
import com.bh.live.pojo.res.user.RoomUserMangerRes;
import com.bh.live.user.service.IRoomMangerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 直播间管理员表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/roomManger")
@Api(tags = "直播间管理员管理")
public class RoomMangerController extends BaseController {

    @Autowired
    private IRoomMangerService roomMangerService;

    @ApiOperation(value = "直播间管理员列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = RoomMangerRes.class, code = 0, message = "直播间管理员列表")})
    @GetMapping("/list")
    public Result list() {
        return Result.success(roomMangerService.getRoomManagers(getHeaderLiveUser().getId()));
    }

    /**
     * 添加管理员
     */
    @ApiOperation(value = "添加直播间管理员", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "添加直播间管理员")})
    @GetMapping("/addManager")
    public Result addManager(@ApiParam("nickname:会员用户名（昵称") @RequestParam("nickname") String nickname) {
        LiveUser hostUser = getHeaderLiveUser();
        return roomMangerService.addLiveRoomManager(nickname, hostUser.getId());
    }

    /**
     * 撤销管理员
     */
    @ApiOperation(value = "撤销直播间管理员", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "撤销直播间管理员")})
    @GetMapping("/cancelManager/{id}")
    public Result cancelManager(@ApiParam("路径参数id") @PathVariable("id") Integer id) {

        return roomMangerService.cancelRoomManager(id);
    }

    /**
     * 特权管理
     *
     * @return
     */
    @ApiOperation(value = "特权管理列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = RoomUserMangerRes.class, code = 0, message = "特权管理列表")})
    @GetMapping("/privilegeList")
    public Result privilegeList() {
        return Result.success(roomMangerService.privilegeManager(getHeaderLiveUser().getId()));
    }

    /**
     * 特权管理
     *
     * @return
     */
    @ApiOperation(value = "特权管理-辞职", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = RoomUserMangerRes.class, code = 0, message = "特权管理-辞职")})
    @GetMapping("/resign")
    public Result resign(@ApiParam("id") @RequestParam("id") Integer id) {
        return Result.success(roomMangerService.managerResign(id));
    }
}

