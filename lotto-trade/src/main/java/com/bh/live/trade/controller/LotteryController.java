package com.bh.live.trade.controller;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.trade.PlayOptRes;
import com.bh.live.pojo.res.trade.PlayTabRes;
import com.bh.live.trade.service.IBetTabService;
import com.bh.live.trade.service.IOrderConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lgs
 * @title: LotteryController
 * @projectName java_live-cp
 * @description: 彩种发布竞猜
 * @date 2019/8/6  20:03
 */
@RestController
@RequestMapping("/lottery")
@Api(tags = "竞猜模块：竞猜选项")
public class LotteryController extends BaseController {

    @Autowired
    private IBetTabService tabService;

    @Autowired
    private IOrderConfigService orderConfigService;


    /**
     * 获取彩种竞猜选项
     *
     * @param seedNo 彩种编号
     * @return
     */
    @GetMapping
    @ApiOperation(value = "竞猜模块-竞猜选项", notes = "竞猜模块-竞猜选项", response = PlayTabRes.class)
    @ApiImplicitParam(name = "seedNo", value = "彩种No", required = true, dataType = "int", paramType = "query")
    public Object tab(Integer seedNo) {
        if(CommonUtil.isEmpty(seedNo)){
            return Result.error(CodeMsg.E_50009);
        }
        return Result.success(tabService.selectTab(seedNo));
    }


    /**
     * 获取彩种发布竞猜价格
     * @param seedNo 彩种编号
     * @return
     */
    @GetMapping("/price-opt")
    @ApiOperation(value = "竞猜模块-竞猜价格", notes = "竞猜模块-竞猜价格", response = PlayOptRes.class)
    @ApiImplicitParam(name = "seedNo", value = "彩种No", required = true, dataType = "int", paramType = "query")
    public Object opt(Integer seedNo) {
        LiveUser liveUser = getHeaderLiveUser();
        if(CommonUtil.isEmpty(seedNo)){
            return Result.error(CodeMsg.E_50009);
        }
        return Result.success(orderConfigService.selectOpt(seedNo,liveUser));
    }
    
    
//    /**
//     * 查询彩票大厅
//     * @return
//     */
//    @GetMapping("/lottoHall")
//    @ApiOperation(value = "查询彩票大厅", response = TradeTransUserRes.class)
//    public Object lottoHall() {
//
//        return Result.success(orderConfigService.lottoHall());
//    }
//
//    /**
//     * 查询彩票大厅
//     * @return
//     */
//    @GetMapping("/guessingHall")
//    @ApiOperation(value = "查询竞猜大厅", response = TradeTransUserRes.class)
//    public Object guessingHall() {
//
//        return Result.success(orderConfigService.guessingHall());
//    }
}
