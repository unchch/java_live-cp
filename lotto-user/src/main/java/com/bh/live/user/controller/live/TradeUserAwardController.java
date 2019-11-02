package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeUserAwardReq;
import com.bh.live.pojo.res.live.ChatUser;
import com.bh.live.pojo.res.trade.TradeUserAwardRes;
import com.bh.live.pojo.res.trade.UserAwardtRankingRes;
import com.bh.live.user.service.ITradeUserAwardService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * <p>
 * 用户打赏记录
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/trade-award")
@Api(tags = "用户打赏记录管理")
public class TradeUserAwardController extends BaseController {

    @Autowired
    ITradeUserAwardService tradeUserAwardService;

    /**
     * 新增打赏记录
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "新增打赏记录", notes = "新增打赏记录", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = TradeUserAwardReq.class, code = 0, message = "打赏记录榜单")})
    @PostMapping("/add")
    public Result add(@RequestBody @ApiParam("打赏记录") TradeUserAwardReq req) {
        LiveUser user = getHeaderLiveUser();
        if (user==null){
            return  Result.error(CodeMsg.E_20001);
        }
        req.setUserId(user.getId());
        ChatUser chatUser=new ChatUser(user.getId(),user.getNickname());
        return tradeUserAwardService.add(req,chatUser,user);
    }

    /**
     * 打赏记录列表
     *
     * @return
     */
    @ApiOperation(value = "打赏记录列表", notes = "打赏记录列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = TradeUserAwardRes.class, code = 0, message = "打赏记录榜单")})
    @GetMapping("/getList")
    public Result getPage(@RequestParam("roomId") @ApiParam("房间id") Integer roomId,
                          @RequestParam("pageNum") @ApiParam("分页") Integer pageNum,
                          @RequestParam("pageSize") @ApiParam("数量") Integer pageSize,
                          @RequestParam("startTime") @ApiParam("直播开始时间") String startTime,
                          @RequestParam("endTime") @ApiParam("直播结束时间") String endTime) {
        try {
            Date start = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, startTime);
            Date end = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, endTime);
            return Result.success(tradeUserAwardService.getList(roomId, pageNum, pageSize,start,end));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.E_70001);
        }

    }

    /**
     * 打赏记录榜单
     *
     * @return
     */
    @ApiOperation(value = "打赏记录榜单", notes = "打赏记录榜单", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = UserAwardtRankingRes.class, code = 0, message = "打赏记录榜单")})
    @GetMapping("/getRankingList")
    public Result getRankingList(@RequestParam("roomId") @ApiParam("房间id") Integer roomId,
                                 @RequestParam("startTime") @ApiParam("直播开始时间") String startTime,
                                 @RequestParam("endTime") @ApiParam("直播结束时间") String endTime) {
        try {
            Date start = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, startTime);
            Date end = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, endTime);
            return Result.success(tradeUserAwardService.getRankingList(roomId, start, end));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.E_70001);
        }

    }

}

