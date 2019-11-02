package com.bh.live.user.controller.live;


import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.anchor.AnchorRes;
import com.bh.live.pojo.res.anchor.GiftInfoRes;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.pojo.res.anchor.LiveAdvanceRes;
import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.pojo.res.inform.IssueEntityRes;
import com.bh.live.pojo.res.live.ChatOpenCode;
import com.bh.live.pojo.res.live.video.Lottery;
import com.bh.live.pojo.res.live.video.LottoIssue;
import com.bh.live.pojo.res.live.video.OpenCode;
import com.bh.live.pojo.res.lottery.HistoryLotteryRes;
import com.bh.live.pojo.res.trade.GuessingDetailRes;
import com.bh.live.pojo.res.trade.GuessingRes;
import com.bh.live.pojo.res.user.AttentionUserRes;
import com.bh.live.rpc.service.inform.IIssueFeignService;
import com.bh.live.rpc.service.trade.ITradeFeignService;
import com.bh.live.user.aop.AnalysisActuator;
import com.bh.live.user.service.ChatRoomService;
import com.bh.live.user.service.IAnchorRoomService;
import com.bh.live.user.service.IHostUserService;
import com.bh.live.user.utils.live.LiveChatUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户主播直播间管理 前端控制器
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/anchor_room")
@Slf4j
@Api(tags = "用户主播直播间管理")
public class AnchorRoomController extends BaseController {

    @Autowired
    private IAnchorRoomService anchorRoomService;

    @Resource
    private ITradeFeignService tradeFeignService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Resource
    private IIssueFeignService issueFeignService;

    @Autowired
    private IHostUserService hostUserService;

    /**
     * 关注主播列表
     *
     * @param seedNo
     * @return
     */
    @ApiOperation(value = "关注列表", notes = "关注列表查询", response = Result.class)
    @GetMapping("/attention_anchor")
    public Result getAttentionAnchor(@RequestParam("seedNo") @ApiParam("彩种ID") Integer seedNo) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) {
            return Result.error(CodeMsg.E_20001);
        }
        return Result.success(anchorRoomService.getAttentionAnchor(liveUser.getId(), seedNo));
    }


    /**
     * 主播推荐列表
     *
     * @param seedNo
     * @return
     */
    @ApiOperation(value = "主播推荐", notes = "主播推荐列表", response = Result.class)
    @GetMapping("/anchor_recommend")
    public Result getAnchorRecommend(@RequestParam("seedNo") @ApiParam("彩种ID") Integer seedNo) {
        Integer userId = null;
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser != null) {
            userId = liveUser.getId();
        }
        return Result.success(anchorRoomService.getAnchorRecommend(userId,seedNo));
    }

    /**
     * 礼物查询
     *
     * @return
     */
    @ApiOperation(value = "礼物", notes = "礼物查询", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = GiftInfoRes.class, code = 0, message = "礼物信息")})
    @GetMapping("/gift")
    public Result getGift() {
        return Result.success(anchorRoomService.getGiftList());
    }


    /**
     * 获取主播信息
     *
     * @param roomId
     * @return
     */
    @AnalysisActuator(note = "直播间在线人数统计")
    @ApiOperation(value = "主播信息", notes = "主播信息", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = AnchorRes.class, code = 0, message = "直播间主播信息")})
    @GetMapping("/hostUserInfo")
    public Result hostUserInfo(@RequestParam("roomId") @ApiParam("房间ID") Integer roomId) {
        Integer userId = null;
        LiveUser user = getHeaderLiveUser();
        if (user != null) {
            userId = user.getId();
        }
        // 根据room获取正在直播的彩种
        Integer seedNo = anchorRoomService.getSeedByRoom(roomId);
        ChatOpenCode openCode = new ChatOpenCode();
        String lotIssueNo =null;
        try {
            // 根据彩种获取最新一期彩果
            IssueCurrentRes res = issueFeignService.selectCurrentIssue(seedNo);
            // 获取上一期开奖
            IssueEntityRes lastIssue = res.getLast();
            // 获取下一期
            IssueEntityRes currentIssue = res.getCurrent();
            openCode.setPeriods(lastIssue.getIssueNo());
            openCode.setOpenCode(lastIssue.getResult());
            openCode.setNextPeriods(currentIssue.getIssueNo());
            long time = currentIssue.getClosingTime().getTime();
            long l = System.currentTimeMillis();
            openCode.setNextTime((time - l));
            lotIssueNo = res.getLast().getIssueNo();
        }catch (Exception e){

            e.printStackTrace();
            throw  e;
        }
        //==============此处数据待修改================================
        openCode.setOpenCode("1,2,3,4,5,6,7,8,9,10");
        //==============此处数据带修改================================
        // 获取主播信息
        AnchorRes info = anchorRoomService.getHostUserInfo(roomId, userId, seedNo);
        info.setSeedNo(seedNo);
        //获取4条直播推荐视频
        List<LiveAdvanceRes> liveRecommend = anchorRoomService.getLiveRecommend();
        // 获取竞猜列表
        List<GuessingRes> guessing = null;
        try {
            guessing = tradeFeignService.getGuessing(seedNo, lotIssueNo);
        } catch (Exception e) {
            log.error("rpc调用竞猜未开奖接口异常:" + e.getMessage(), e);
        }
        // 历史彩果
        List<HistoryLotteryRes> lottoSeed = anchorRoomService.getLottoSeed(seedNo);
        //=========================================封装数据返回==================================================================
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("roomId", roomId);
        result.put("anchor", info);
        result.put("token", getToken(roomId));
        result.put("liveRec", liveRecommend);
        result.put("currLoginRole", getRole(roomId, user));
        result.put("seed", openCode);
        result.put("guessing", guessing);
        result.put("seedHistory", lottoSeed);
        return Result.success(result);
    }

    /**
     * 获取当前登录用户在直播间的角色
     *
     * @param room
     * @param liveUser
     * @return
     */
    private String getRole(Integer room, LiveUser liveUser) {
        // 角色 user/manager/anchor
        // 判断是否是房管
        if (liveUser != null) {
            Integer count = chatRoomService.findManager(room, liveUser.getId());
            if (count > 0) {
                return "manager";
            } else if (liveUser.getIsAnchor() == 1) {
                // 判断登录用户是会否是本场主播
                Integer hId = chatRoomService.findCurrentAnchor(room);
                if (hId == liveUser.getId()) {
                    return "anchor";
                }
            }
        }
        return "user";
    }

    /**
     * 获取websocket 管道流token
     *
     * @param room
     * @return
     */
    public Object getToken(Integer room) {
        String url = "api/getToken?room=" + room;
        LiveUser user = getHeaderLiveUser();
        if (user != null) {
            url += "&userId=" + user.getId();
        }
        Connection con = LiveChatUtil.getConect(url);
        Connection.Response res = null;
        try {
            res = con.execute();
            if (res.statusCode() == 200) {
                return JSONObject.parse(res.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceRuntimeException(CodeMsg.E_90010);
        }
        return null;
    }

    /**
     * 修改房间公告
     */
    @ApiOperation(value = "修改房间公告", notes = "修改房间公告", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = AttentionUserRes.class, code = 0, message = "修改房间公告")})
    @GetMapping("/updateNotice")
    public Result updateNotice(@ApiParam("公告") @RequestParam("notice") String notice) {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        HostUserFormRes hostUser = hostUserService.getHostUserById(liveUser.getId());
        if (hostUser == null) return Result.error(CodeMsg.E_10004);
        Result result = null;
        try {
            result = anchorRoomService.updateRoomNotice(notice, hostUser.getRoomId());
        } catch (Exception e) {
            result = new Result(500, e.getMessage());
        }
        return result;
    }

    /**
     * 获取主播房间公告
     */
    @ApiOperation(value = "获取主播房间公告", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = String.class, code = 0, message = "获取主播房间公告")})
    @GetMapping("/getNotice")
    public Result getNotice() {
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) return Result.error(CodeMsg.E_20004);
        HostUserFormRes hostUser = hostUserService.getHostUserById(liveUser.getId());
        if (hostUser == null) return Result.error(CodeMsg.E_10004);
        Map<String, Object> resMap = new HashMap<>();
        String result = "";
        try {
            result = hostUserService.getHostNotice(hostUser.getUserId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
        resMap.put("notice", result == null ? "" : result);
        resMap.put("roomId", hostUser.getRoomId());
        return Result.success(resMap);
    }

    /**
     * 竞猜详情
     */
    @ApiOperation(value = "竞猜详情", notes = "竞猜详情", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = GuessingDetailRes.class, code = 0, message = "竞猜详情")})
    @GetMapping("/guessingDetail")
    public Result guessingDetail(@ApiParam("列表详情id") @RequestParam("id") Integer id) {
        Integer userId = 0;
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser != null) userId = liveUser.getId();
        Result result = null;
        try {
            GuessingDetailRes detailRes = tradeFeignService.guessingDetail(id, userId);
            result = Result.success(detailRes);
        } catch (Exception e) {
            log.error(e.getMessage() + e.getCause());
            result = Result.error(e.getMessage());
        }
        return result;
    }

    /**
     * 获取开奖视频
     *
     * @param roomId
     * @return
     */
    @ApiOperation(value = "开奖视频", notes = "开奖视频", response = Result.class)
    @GetMapping("/getResult")
    public Result getResult(@RequestParam("roomId") @ApiParam("房间ID") Integer roomId) {
        // 根据room获取正在直播的彩种
        Integer seedNo = anchorRoomService.getSeedByRoom(roomId);
        IssueCurrentRes res = issueFeignService.selectCurrentIssue(seedNo);
        // 获取当前开奖期
        IssueEntityRes issueCurrent = res.getCurrent();
        // 获取上一期
        IssueEntityRes issueLast = res.getLast();
        Map<String, Object> data = new HashMap();
        OpenCode openCode = new OpenCode();
        openCode.setExpect(issueLast.getIssueNo());
        openCode.setOpenCode(issueLast.getResult());
        data.put("lotteryResult", openCode);
        LottoIssue current = new LottoIssue();
        current.setPeriodNumber(issueLast.getIssueNo());
        String awardTime = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS).format(issueLast.getOpenTime());
        current.setAwardTime(awardTime);
        current.setAwardNumbers(issueLast.getResult());
        LottoIssue next = new LottoIssue();
        next.setPeriodNumber(issueCurrent.getIssueNo());
        awardTime = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS).format(issueCurrent.getOpenTime());
        next.setAwardTime(awardTime);
        next.setAwardTimeInterval("3000");
        next.setDelayTimeInterval("1");
        Map<String, Object> bjsc = new HashMap();
        bjsc.put("current", current);
        bjsc.put("next", next);
        Lottery lottery = new Lottery();
        lottery.setAbbreviation("bjsc");
        lottery.setEndFlash("");
        lottery.setRoomId(roomId.toString());
        lottery.setSeconds("4");
        lottery.setNextExpect(issueCurrent.getPosition());
        data.put("lottery", lottery);
        data.put("label", getLabel(issueLast.getResult()));
        data.put("bjsc", bjsc);
        return Result.success(data);
    }


    /**
     * 设置单双大小
     *
     * @param result
     * @return
     */
    public Map<String, Object> getLabel(String result) {
        Map<String, Object> label = new HashMap();
        if (result != null) {
            String[] split = result.split(",");
            int[] num = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                num[i] = Integer.valueOf(split[i]);
            }
            int sum = num[0] + num[1];
            String bs = "小";
            String sd = "单";
            if (sum > 11) {
                bs = "大";
            }
            if (sum % 2 == 0) {
                sd = "双";
            }
            label.put("sum", sum);
            label.put("big", bs);
            label.put("single", sd);
            // 获取龙虎
            label.put("longHu", getLongHu(num));
            return label;
        }
        return label;
    }

    /**
     * 设置龙虎
     *
     * @param num
     * @return
     */
    public String getLongHu(int[] num) {
        StringBuffer sbuffer = new StringBuffer();
        for (int i = 0; i < num.length / 2; i++) {
            int sum = num[i] - num[num.length - 1 - i];
            if (sum > 0) {
                sbuffer.append("龙");
            } else {
                sbuffer.append("虎");
            }
        }
        return sbuffer.toString();
    }


}

