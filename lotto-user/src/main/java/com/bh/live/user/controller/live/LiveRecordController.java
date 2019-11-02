package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.anchor.LiveRecordRes;
import com.bh.live.user.service.ILiveRecordService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 主播直播记录 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
@RestController
@RequestMapping("/live-record")
@Api(tags = "直播记录")
@Slf4j
public class LiveRecordController extends BaseController {

    @Autowired
    private ILiveRecordService liveRecordService;

    /**
     * 直播记录
     *
     * @return
     */
    @ApiOperation(value = "直播记录", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = LiveRecordRes.class, code = 0, message = "直播记录")})
    @GetMapping("/recordList")
    public Result recordList(@ApiParam("查询开始时间") @RequestParam(value = "begin", required = false) String begin,
                             @ApiParam("查询结束时间") @RequestParam(value = "end", required = false) String end) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        try {
            return Result.success(liveRecordService.getRecords(liveUser.getId(), begin, end));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Result(500, e.getMessage());
        }
    }


}

