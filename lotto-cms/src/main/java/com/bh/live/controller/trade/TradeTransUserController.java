package com.bh.live.controller.trade;


import com.bh.live.common.constant.JxlsTemplateConst;
import com.bh.live.common.utils.jxls.JxlsExcelUtil;
import com.bh.live.model.trade.vo.OrderVO;
import com.bh.live.utils.PropUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bh.live.common.result.Result;
import com.bh.live.model.trade.TradeTransUser;
import com.bh.live.model.trade.TradeTransUserData;
import com.bh.live.pojo.res.trade.TradeTransDetailRes;
import com.bh.live.service.trade.ITradeTransUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户流水表 前端控制器
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@RestController
@RequestMapping("/tradeTrans")
@Slf4j
@Api(tags = "用户流水管理")
public class TradeTransUserController {

    @Autowired
    private ITradeTransUserService transUserService;

    @ApiOperation(value = "用户流水列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = TradeTransUser.class, code = 0, message = "用户流水列表")})
    @PostMapping("/list")
    public Result listByCondition(@RequestBody TradeTransUser tradeTrans) {
    	
        return Result.success(transUserService.queryUserTransPage(tradeTrans));
    }
    
    @ApiOperation(value = "流水总数据", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = TradeTransUserData.class, code = 0, message = "流水数据信息")})
    @GetMapping("/flowAllData")
    public Result flowAllData(TradeTransUser tradeTrans) {
    	
        return Result.success(transUserService.flowAllData(tradeTrans));
    }
    
	@ApiOperation(value = "查询用户消费详情", response = TradeTransDetailRes.class)
	@GetMapping("/queryTransDetailById")
	public Result queryTransDetailById(@ApiParam("流水Id") @RequestParam("transId") String transId) {
		return Result.success(transUserService.queryTransDetailById(transId));
	}


    @GetMapping("/export")
    @ApiOperation(value = "导出用户交易流水")
    public void export(TradeTransUser tradeTrans, HttpServletResponse response) {
        new JxlsExcelUtil<>(PropUtil.fileExportUrl, transUserService.queryUserTransExcel(tradeTrans), JxlsTemplateConst.USER_TRADE_TRANS_TEMPLATE, response);
    }

}

