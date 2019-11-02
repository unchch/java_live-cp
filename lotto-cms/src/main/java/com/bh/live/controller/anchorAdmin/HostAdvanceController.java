package com.bh.live.controller.anchorAdmin;

import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.pojo.req.anchor.LiveSeedReq;
import com.bh.live.pojo.res.anchor.AdvancesRes;
import com.bh.live.pojo.res.anchor.LiveSeedRes;
import com.bh.live.service.anchor.IHostAdvanceService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 主播预告表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@RestController
@Api(tags = "主播预告操作")
@RequestMapping("/host/advance")
@Slf4j
public class HostAdvanceController {

    @Autowired
    private IHostAdvanceService advanceService;

    /**
     * 预告列表
     *
     * @return
     */
    @ApiOperation(value = "列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = AdvancesRes.class, code = 0, message = "预告返回对象")})
    @GetMapping("/list")
    public Result list(@ApiParam("彩种编号") @RequestParam("seedNo") Integer seedNo) {
        try {
            return Result.success(advanceService.getHostAdvances(seedNo, "oneSeed"));
        } catch (Exception e) {
            log.error(e.getMessage() + "---" + e.getCause());
            return Result.error(CodeMsg.E_90004);
        }
    }
}
