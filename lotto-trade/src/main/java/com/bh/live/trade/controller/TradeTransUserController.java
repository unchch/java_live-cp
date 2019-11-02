package com.bh.live.trade.controller;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.trade.TradeUserReq;
import com.bh.live.pojo.res.trade.TradeTransDetailRes;
import com.bh.live.pojo.res.trade.TradeTransUserRes;
import com.bh.live.trade.service.ITradeTransUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户流水表 前端控制器
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@RestController
@RequestMapping("/trade/trans/user")
@Api(tags = "流水记录表")
public class TradeTransUserController extends BaseController{


}
