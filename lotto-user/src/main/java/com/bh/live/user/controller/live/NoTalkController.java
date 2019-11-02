package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.model.anchor.NoTalk;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.anchor.NoTalkRes;
import com.bh.live.pojo.res.user.RoomMangerRes;
import com.bh.live.user.service.INoTalkService;
import com.bh.live.user.service.IRoomMangerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 直播间禁言表 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/no-talk")
@Api(tags = "直播间禁言管理")
public class NoTalkController extends BaseController {

    @Autowired
    private INoTalkService noTalkService;

    @ApiOperation(value = "直播间禁言列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = NoTalkRes.class, code = 0, message = "直播间禁言列表")})
    @GetMapping("/list")
    public Result list(@ApiParam("用户昵称(查询时传参)") @RequestParam(value = "nickname", required = false) String nickname,
                       @ApiParam("查询类型 undo(解禁记录)；user(禁言用户)；IP(禁言ip，参数值必须大写)")
                       @RequestParam(value = "queryType") String queryType) {
        return noTalkService.noTalkList(nickname, queryType, getHeaderLiveUser().getId());
    }

    /**
     * 添加禁言
     */
    @ApiOperation(value = "添加禁言用户或IP", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "添加禁言用户或IP")})
    @PostMapping("/addNoSpeech")
    public Result addNoSpeech(@RequestBody @ApiParam("value:会员用户名（昵称）/ IP;  " +
            "time（int）:禁言时长--0表示永久;  " +
            "timeType:时间类型minute，hour,day;  " +
            "type(int):禁言类型（1用户，2 IP）； " +
            "remark:禁言原因（取下拉值）") Map<String, Object> params) {
        String value = params.get("value").toString();
        Integer time = Integer.valueOf(params.get("time").toString());
        String timeType = params.get("timeType").toString();
        Integer type = Integer.valueOf(params.get("type").toString());
        String remark = params.get("remark").toString();
        if (StringUtils.isEmpty(value) && time == null && type == null && StringUtils.isEmpty(timeType)) {
            return Result.error(CodeMsg.E_90003);
        }
        return noTalkService.addNoTalk(value, time, timeType, type, remark, getHeaderLiveUser());
    }

    /**
     * 解禁
     */
    @ApiOperation(value = "解禁禁言", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = Result.class, code = 0, message = "解禁禁言")})
    @GetMapping("/cancel/{id}")
    public Result cancel(@ApiParam("路径参数id") @PathVariable("id") Integer id) {
        NoTalk noTalk = new NoTalk();
        noTalk.setNoTalkId(id);
        noTalk.setStatus(1);
        boolean b = noTalkService.updateById(noTalk);
        if (b) return Result.success();
        return Result.error(CodeMsg.E_500);
    }

}

