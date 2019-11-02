package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.StatUserIncome;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.user.GiftListIncomeRes;
import com.bh.live.pojo.res.user.RoomMangerRes;
import com.bh.live.user.service.IStatUserIncomeService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 主播礼物收入统计表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
@RestController
@RequestMapping("/user-income")
@Api(tags = "直播收入-礼物收入")
@Slf4j
public class StatUserIncomeController extends BaseController {

    @Autowired
    private IStatUserIncomeService userIncomeService;

    @ApiOperation(value = "礼物收入列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = StatUserIncome.class, code = 0, message = "礼物收入列表")})
    @GetMapping("/giftIncome")
    public Result giftIncomeList(@ApiParam("(查询条件日期)date,userId(当前用户),pageSize,pageNum") @RequestParam Map<String, Object> params) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        params.put("userId", liveUser.getId());
        TableDataInfo dataInfo = null;
        try {
            dataInfo = userIncomeService.selectUserIncome(params);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        return Result.success(dataInfo);
    }

    @ApiOperation(value = "礼物清单", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = GiftListIncomeRes.class, code = 0, message = "礼物清单列表")})
    @GetMapping("/giftDonate")
    public Result giftDonateList(@ApiParam("(查询条件日期)begin,end,userId(当前用户),pageSize,pageNum") @RequestParam Map<String, Object> params) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        params.put("userId", liveUser.getId());
        TableDataInfo dataInfo = null;
        try {
            dataInfo = userIncomeService.giftDonateList(params);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_500);
        }
        return Result.success(dataInfo);
    }


}

