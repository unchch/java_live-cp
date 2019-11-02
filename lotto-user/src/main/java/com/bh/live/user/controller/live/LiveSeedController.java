package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.Attention;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.live.LiveRoomSeedRes;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;
import com.bh.live.user.dao.HostUserDao;
import com.bh.live.user.service.ILiveSeedService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 直播彩种管理 前端控制器
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/liveSeed")
@Api(tags = "直播彩种管理")
@Slf4j
public class LiveSeedController extends BaseController {

    @Autowired
    private ILiveSeedService liveSeedService;

    @Resource
    private HostUserDao hostUserDao;

    /**
     * 彩种列表
     *
     * @return
     */
    @ApiOperation(value = "彩种列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = SeedCategoryRes.class, code = 0, message = "彩种返回对象")})
    @GetMapping("/list")
    public Result list() {
        try {
            return Result.success(liveSeedService.getCategoryLiveSeed());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
    }

    /**
     * 彩种直播列表
     *
     * @return
     */
    @ApiOperation(value = "彩种直播列表", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = LiveRoomSeedRes.class, code = 0, message = "直播列表")})
    @GetMapping("/liveList")
    public Result liveList(@ApiParam("彩种编号") @RequestParam("seedNo") Integer seedNo,
                           @ApiParam("查询排序类型(default:默认；number:人数；level:等级；time：时间)")
                           @RequestParam("queryType") String queryType) {
        boolean logined = true;
        LiveUser liveUser = getHeaderLiveUser();
        //如果为空，表示未登录用户查看，直接设置未关注主播
        if (liveUser == null) {
            logined = false;
        }
        try {
            List<LiveRoomSeedRes> roomInfo = liveSeedService.getLiveUserRoomInfo(seedNo, queryType);
            if (roomInfo == null || roomInfo.size() == 0) {
                return Result.success();
            }
            List<Attention> attentions = null;
            //登录了才去查询是否关注
            if (logined) {
                List<Integer> ids = roomInfo.stream().map(LiveRoomSeedRes::getUserId).collect(Collectors.toList());
                attentions = hostUserDao.getUserAttention(null, liveUser.getId(), "list", ids);
            }
            for (LiveRoomSeedRes res : roomInfo) {
                if (logined) {
                    for (Attention attention : attentions) {
                        if (res.getUserId().compareTo(attention.getTargetId()) == 0 &&
                                liveUser.getId().compareTo(attention.getUserId()) == 0) {
                            res.setIsAttent(attention.getIsAttent());
                        }
                    }
                } else {
                    res.setIsAttent(0);
                }
            }
            return Result.success(roomInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.E_90004);
        }
    }
}

