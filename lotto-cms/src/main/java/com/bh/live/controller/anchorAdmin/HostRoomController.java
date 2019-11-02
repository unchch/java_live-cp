package com.bh.live.controller.anchorAdmin;


import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.anchor.HostRoomReq;
import com.bh.live.pojo.req.anchor.LottoTypeReq;
import com.bh.live.pojo.res.anchor.RoomLiveRecord;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.anchor.IHostRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 直播间 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/hostRoom")
@Api(tags = "直播间管理")
@Slf4j
public class HostRoomController {

    @Autowired
    private IHostRoomService hostRoomService;

    /**
     * 直播间列表
     *
     * @return
     */
    @ApiOperation(value = "直播间列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = RoomLiveRecord.class, code = 0, message = "直播间列表")})
    @GetMapping("/list")
    public Result list(HostRoomReq req) {
        try {
            TableDataInfo byCondition = hostRoomService.getByCondition(req);
            return Result.success(byCondition);
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e instanceof NullPointerException) {
                return new Result(500, "必要数据为空，请检查数据是否完整");
            }
            return Result.error(e.getMessage());
        }

    }

    /**
     * 开播
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "开播", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "开播")})
    @GetMapping("/openLive")
    public Result openLive(HostRoomReq req) {
        try {
            hostRoomService.openRoomLive(req);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 结束直播
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "结束直播", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "结束直播")})
    @GetMapping("/endLive")
    public Result endLive(HostRoomReq req) {
        try {
            hostRoomService.endRoomLive(req);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 关闭/启用直播间
     *
     * @return
     */
    /*@ApiOperation(value = "关闭/启用直播间", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "关闭/启用直播间")})
    @GetMapping("/closeOrUseRoom")
    public Result closeOrUseRoom(@ApiParam("房间号id") @RequestParam("id") Integer id,
                                 @ApiParam("关闭0/启用1") @RequestParam("status") Integer status) {
        try {
            hostRoomService.closeOrOpenRoom(id, status);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
        return Result.success();
    }
    */

    /**
     * 强制踢下线（限时）
     *
     * @return
     */
    /*@ApiOperation(value = "强制踢下线", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "强制踢下线")})
    @GetMapping("/kickOffLineUser")
    public Result kickOffLineUser(@ApiParam("房间号id") @RequestParam("id") Integer id,
                                  @ApiParam("主播id") @RequestParam("hostId") Integer hostId,
                                  @ApiParam("踢下线原因") @RequestParam("kickOffLine") Integer kickOffLine) {
        try {
            hostRoomService.kickOffLine(id, hostId, kickOffLine);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }*/

    /**
     * 修改直播间配置
     *
     * @return
     */
    @ApiOperation(value = "修改直播间配置", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "修改直播间配置")})
    @GetMapping("/updateRoom")
    public Result updateRoom(HostRoomReq req) {
        try {
            hostRoomService.update(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 首页 -> 彩种直播
     *
     * @param lottoTypeReq seedName 彩种名称 type
     */
    @GetMapping("/seedNameList")
    public Result queryHostRoomList(LottoTypeReq lottoTypeReq) {
        return Result.success(hostRoomService.queryHostRoomList(lottoTypeReq));
    }
}

