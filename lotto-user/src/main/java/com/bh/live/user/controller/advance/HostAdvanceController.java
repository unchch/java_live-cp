package com.bh.live.user.controller.advance;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.anchor.HostAdvanceReq;
import com.bh.live.pojo.req.anchor.SaveHostAdvance;
import com.bh.live.pojo.res.anchor.AdvanceRes;
import com.bh.live.pojo.res.anchor.HostAdvanceRes;
import com.bh.live.pojo.res.anchor.RecomHostRoomRes;
import com.bh.live.pojo.res.user.LiveHostStateRes;
import com.bh.live.user.service.IHostAdvanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页列表 前端控制器
 * </p>
 *
 * @author WW
 * @since 2019-07-29
 */
@RestController
@Api(tags = "主播预告操作")
@RequestMapping("/host/advance")
public class HostAdvanceController extends BaseController {

    @Resource
    private IHostAdvanceService hostAdvanceService;

    @ApiOperation(value = "添加预告", response = SaveHostAdvance.class)
    @RequestMapping(value = "/addHostAdvance", method = RequestMethod.POST)
    public Result addHostAdvance(@RequestBody SaveHostAdvance save) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) {
            return Result.error(CodeMsg.E_20001);
        }
        //参数非空判断
        if (save.getEndTime() == null || save.getLiveDate() == null
                || save.getSeedNo() == null || save.getLivePeriod() == null) {
            return Result.error(CodeMsg.E_90003);
        }
        return Result.success(hostAdvanceService.addHostAdvance(getHeaderLiveUser().getId(), save));
    }

    @ApiOperation(value = "编辑预告", response = Boolean.class)
    @RequestMapping(value = "/updateHostAdvance", method = RequestMethod.POST)
    public Result updateHostAdvance(@RequestBody HostAdvanceReq hostAdvance) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) {
            return Result.error(CodeMsg.E_20001);
        }
        hostAdvance.setHostId(liveUser.getId());
        return Result.success(hostAdvanceService.updateHostAdvance(hostAdvance));
    }

    @ApiOperation(value = "查询预告列表", response = HostAdvanceRes.class)
    @RequestMapping(value = "/queryHostAdvance", method = RequestMethod.GET)
    public Result queryHostAdvance(@ApiParam("彩种类型") @RequestParam(value = "seedNo", required = false) Integer seedNo,
                                   @ApiParam("主播id") @RequestParam(value = "hostId", required = false) Integer hostId,
                                   @ApiParam("多少天内的预告") @RequestParam(value = "size", defaultValue = "1") Integer size) {
        hostId = hostId != null ? hostId : getHeaderLiveUser() != null ? getHeaderLiveUser().getId() : null;
        return Result.success(hostAdvanceService.queryHostAdvance(hostId, seedNo, size));
    }

    @ApiOperation(value = "根据彩种编号查询今日明日预告列表", response = HostAdvanceRes.class)
    @RequestMapping(value = "/queryClassifyHostAdvance", method = RequestMethod.GET)
    public Result queryClassifyHostAdvance(@ApiParam("彩种类型") @RequestParam(value = "seedNo") Integer seedNo) {
        Integer hostId = null;//getHeaderLiveUser() != null ? getHeaderLiveUser().getId() : null;
        return Result.success(hostAdvanceService.queryClassifyHostAdvance(hostId, seedNo));
    }

    @ApiOperation(value = "查询所有彩种主播直播预告列表", response = AdvanceRes.class)
    @RequestMapping(value = "/queryLottoAllHostAdvance", method = RequestMethod.GET)
    public Result queryLottoAllHostAdvance(){
        return Result.success(hostAdvanceService.queryAllHostAdvance());
    }

    @ApiOperation(value = "查询推荐主播列表", response = HostAdvanceRes.class)
    @RequestMapping(value = "/queryHostUserListBySize", method = RequestMethod.GET)
    public Result queryHostUserListBySize(
            @ApiParam("推荐条数") @RequestParam(value = "pageSize",required = false) Integer size) {
        Integer hostId = getHeaderLiveUser() != null ? getHeaderLiveUser().getId() : null;
        return Result.success(hostAdvanceService.queryHostUserListBySize(hostId, size));
    }

    @ApiOperation(value = "查询热门直播列表", response = RecomHostRoomRes.class)
    @RequestMapping(value = "/queryHostRoomListBySize", method = RequestMethod.GET)
    public Result queryHostRoomListBySize(
            @ApiParam("推荐条数") @RequestParam(value = "pageSize", defaultValue = "10") Integer size) {

        return Result.success(
                hostAdvanceService.queryHostRoomListBySize(getHeaderLiveUser() != null ? getHeaderLiveUser().getId() : null, size));
    }

    @ApiOperation(value = "首页搜索", response = RecomHostRoomRes.class)
    @RequestMapping(value = "/homeSearch", method = RequestMethod.GET)
    public Result homeSearch(@ApiParam("搜索内容") @RequestParam(value = "content", required = false) String content,
                             @ApiParam("查询类型  0专家  1主播") @RequestParam(value = "userType", defaultValue = "0") Integer userType,
                             @ApiParam("查询行数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam("页数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        Integer hostId = getHeaderLiveUser() != null ? getHeaderLiveUser().getId() : null;
        return Result.success(hostAdvanceService.homeSearch(hostId, content, userType, pageNum, pageSize));
    }

    @ApiOperation(value = "查询主播的预告列表", response = HostAdvanceRes.class)
    @RequestMapping(value = "/queryAdvanceByHostId", method = RequestMethod.GET)
    public Result queryAdvanceByHostId() {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) {
            return Result.error(CodeMsg.E_20001);
        }
        return Result.success(hostAdvanceService.queryAdvanceByHostId(liveUser.getId()));
    }

    @ApiOperation(value = "查询主播状态", response = LiveHostStateRes.class)
    @RequestMapping(value = "/queryLiveHostStateById", method = RequestMethod.GET)
    public Result queryLiveHostStateById(@ApiParam("主播id") @RequestParam(value = "hostId", required = true) Integer hostId) {

        return Result.success(hostAdvanceService.queryLiveHostStateById(hostId));
    }

}
