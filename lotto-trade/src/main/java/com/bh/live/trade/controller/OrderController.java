package com.bh.live.trade.controller;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.pojo.res.trade.GuessingDetailRes;
import com.bh.live.pojo.res.trade.GuessingRes;
import com.bh.live.trade.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/order")
@Api(tags = "竞猜模块：发布竞猜")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/user")
    @ApiOperation(value = "用户中心-我的竞猜", notes = "用户中心-我的竞猜", response = Result.class)
    public Object findOrderByUser(String userId, String lotteryCode) {
        Assert.isNotNull(userId, CodeMsg.E_50008);
        Assert.isNotNull(lotteryCode, CodeMsg.E_50008);

        return orderService.page(null);
    }

    @PostMapping()
    @ApiOperation(value = "竞猜中心-发布竞猜", notes = "竞猜中心-发布竞猜", response = Result.class)
    public Object insert(@RequestBody OrderReq req, HttpServletRequest request) {
        LiveUser liveUser = getHeaderLiveUser();
        orderService.insert(req, liveUser);
        return Result.success();
    }

    /**
     * 竞猜列表提供rpc接口
     *
     * @param seedNo
     * @param lotIssueNo
     * @return
     * @author Morphon
     */
    @GetMapping(value = "/getGuessing")
    public List<GuessingRes> getGuessing(@RequestParam("seedNo") Integer seedNo, @RequestParam("lotIssueNo") String lotIssueNo) {
        return orderService.selectGuess(seedNo, lotIssueNo);
    }

    /**
     * 竞猜详情提供rpc接口
     *
     * @param id
     * @return
     * @author Morphon
     */
    @GetMapping("/guessingDetail")
    GuessingDetailRes guessingDetail(@RequestParam("id") Integer id, @RequestParam("userId") Integer userId) {
        return orderService.getGuessDetail(id, userId);
    }
}
