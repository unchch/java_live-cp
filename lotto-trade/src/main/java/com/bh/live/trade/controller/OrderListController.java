package com.bh.live.trade.controller;

import cn.hutool.core.util.ObjectUtil;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.trade.Order;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.AwardTradeReq;
import com.bh.live.pojo.req.trade.OrderFullReq;
import com.bh.live.pojo.req.trade.OrderHistoryFullReq;
import com.bh.live.pojo.req.trade.TradeUserReq;
import com.bh.live.pojo.res.trade.OrderFullRes;
import com.bh.live.pojo.res.trade.TradeTransDetailRes;
import com.bh.live.pojo.res.trade.TradeTransUserRes;
import com.bh.live.pojo.res.trade.UserOrderStatisticsRes;
import com.bh.live.trade.service.IOrderService;
import com.bh.live.trade.service.ITradeTransUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 竞猜 前端控制器
 * </p>
 *
 * @author ww
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/guessingOrderList")
@Api(tags = "竞猜模块：竞猜列表")
public class OrderListController extends BaseController {

    @Resource
    private IOrderService orderService;

    @Resource
    private ITradeTransUserService tradeTransUserService;

    @ApiOperation(value = "竞猜列表", response = OrderFullRes.class)
    @RequestMapping(value = "/queryOrderList", method = RequestMethod.POST)
    public Result queryOrderList(@ApiParam("方案入参对象") @RequestBody OrderFullReq req) {
        LiveUser liveUser = getHeaderLiveUser();
        Integer userId = liveUser != null ? liveUser.getId() : null;
        if (userId != null) {
            req.setUserId(userId);
        }
        return Result.success(orderService.queryOrderList(req));
    }

    @ApiOperation(value = "查询历史订单返回信息", response = Order.class)
    @RequestMapping(value = "/queryOrderListHistory", method = RequestMethod.POST)
    public Result queryOrderListHistoryByUserId(@ApiParam("历史方案入参对象") @RequestBody OrderHistoryFullReq req) {
        if (ObjectUtil.isNotEmpty(req.getUserId())) {//前端入参主播id
            LiveUser liveUser = getHeaderLiveUser();
            req.setCurUserId(liveUser != null ? liveUser.getId() : null);//如果用户登录，则获取当前登录的用户id
        } else {//前端未传主播id
            LiveUser user = getHeaderLiveUser();
            if (user == null) {
                return Result.error(CodeMsg.E_20001);
            }
            req.setUserId(user.getId());
            req.setCurUserId(user.getId());
        }
//    	req.setCurUserId(req.getUserId()!=null?req.getUserId():getHeaderLiveUser()!=null?getHeaderLiveUser().getId():null);//当前用户id
        return Result.success(orderService.queryOrderListHistoryByUserId(req));
    }

    @ApiOperation(value = "查询用户开奖统计信息 当前连赢 指标 10期 20期 30期 50期 今日 昨日 赢率 盈利", response = UserOrderStatisticsRes.class)
    @RequestMapping(value = "/queryUserOrderStatistics", method = RequestMethod.GET)
    public Result queryUserOrderStatistics(@ApiParam("用户id") @RequestParam(value = "userId", required = false) Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            LiveUser user = getHeaderLiveUser();
            userId = user.getId();
        }
        if (ObjectUtil.isEmpty(userId)) {
            return Result.success();
        }
        return Result.success(orderService.queryUserOrderStatistics(userId));
    }


    @ApiOperation(value = "彩票大厅", response = UserOrderStatisticsRes.class)
    @RequestMapping(value = "/queryLottoLobby", method = RequestMethod.GET)
    public Result queryLottoLobby(/*@ApiParam("查询大小") @RequestParam(value = "size",defaultValue = "10") Integer size*/) {

        return Result.success();
    }

    @ApiOperation(value = "竞猜大厅", response = UserOrderStatisticsRes.class)
    @RequestMapping(value = "/queryGuessingLobby", method = RequestMethod.GET)
    public Result queryGuessingLobby(/*@ApiParam("查询大小") @RequestParam(value = "size",defaultValue = "10") Integer size*/) {

        return Result.success();
    }

    @ApiOperation(value = "查询用户消费流水", response = TradeTransUserRes.class)
    @PostMapping("/queryTransPage")
    public Result queryTransPage(@ApiParam("流水入参对象") @RequestBody TradeUserReq req) {
        LiveUser liveUser = getHeaderLiveUser();
        Integer userId = liveUser != null ? liveUser.getId() : null;
        if (userId == null) {
            return Result.error(CodeMsg.E_20001);
        }
        req.setUserId(userId);//用户id
        return Result.success(tradeTransUserService.queryTransPage(req));
    }

    @ApiOperation(value = "查询用户消费详情", response = TradeTransDetailRes.class)
    @GetMapping("/queryTransDetailById")
    public Result queryTransDetailById(@ApiParam("流水Id") @RequestParam("transId") String transId) {
        return Result.success(tradeTransUserService.queryTransDetailById(transId));
    }

    @ApiOperation(value = "查询用户打赏流水", response = AwardTradeReq.class)
    @PostMapping("/queryTradeAllTransUser")
    public Result queryTradeAllTransUser(@ApiParam("打赏流水入参对象") @RequestBody AwardTradeReq req) {
        LiveUser liveUser = getHeaderLiveUser();
        Integer userId = liveUser != null ? liveUser.getId() : null;
        if (userId == null) {
            return Result.error(CodeMsg.E_20001);
        }
        req.setUserId(userId);
        return Result.success(tradeTransUserService.queryTradeAllTransUser(req));
    }

    @ApiOperation(value = "查询彩种玩法", response = AwardTradeReq.class)
    @GetMapping("/queryLottoPlayNoAndName")
    public Result queryLottoPlay(@ApiParam("彩种编号") @RequestParam("seedNo") Integer seedNo) {

        return Result.success(orderService.queryPlayNoAndNameBySeedNo(seedNo));
    }
}
