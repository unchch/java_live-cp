package com.bh.live.user.controller.live;

import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.enums.ChatMsgTypeEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.model.anchor.KeywordShield;
import com.bh.live.model.cms.Keyword;
import com.bh.live.model.user.ChatLog;
import com.bh.live.pojo.req.live.BaseMsgReq;
import com.bh.live.pojo.req.live.ChatLogReq;
import com.bh.live.user.service.IKeywordService;
import com.bh.live.user.service.IKeywordShieldService;
import com.bh.live.user.utils.live.LiveChatUtil;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.live.MsgNormalReq;
import com.bh.live.pojo.res.live.ChatUser;
import com.bh.live.user.service.ChatRoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description: 聊天室管理
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/6 10:19
 */
@RestController
@RequestMapping("/chat")
@Api(tags = "直播间聊天室管理")
public class ChatRoomController extends BaseController {

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    private IKeywordService keywordService;

    @Autowired
    private IKeywordShieldService keywordShieldService;

    /**
     * 获得所有列表数据
     *
     * @return
     */
    @ApiOperation(value = "聊天记录", notes = "聊天记录", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = ChatLog.class, code = 0, message = "聊天记录")})
    @PostMapping("/page")
    public Result getChatList(@RequestBody @ApiParam("聊天记录") ChatLogReq chatLog) {
        return Result.success(chatRoomService.getPage(chatLog));
    }


    /**
     * 发送普通消息
     * <p>
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "聊天内容", notes = "聊天内容", response = Result.class)
    @PostMapping("/msg/speak")
    public Result msgSpeak(@RequestBody @ApiParam("聊天内容") MsgNormalReq req) {
        // 获取登录用户
        LiveUser liveUser = getHeaderLiveUser();
        // 判断用户是否被禁言
        Integer noTalk = chatRoomService.isNoTalk(req.getHostId(), liveUser.getId());
        if (noTalk > 0) {
            return Result.error(CodeMsg.E_90002);
        }
        // 平台全局敏感词过滤
        req.setContent(excludeKeyword(req.getContent(), Integer.valueOf(req.getRoom())));
        ChatUser user = new ChatUser();
        user.setUserId(liveUser.getId());
        user.setNickName(liveUser.getNickname());
        // 角色 user/manager/anchor
        // 判断是否是房管
        Integer count = chatRoomService.findManager(Integer.parseInt(req.getRoom()), liveUser.getId());
        if (count > 0) {
            user.setRole("manager");
        } else if (liveUser.getIsAnchor() == 1) {
            // 判断登录用户是会否是本场主播
            Integer hId = chatRoomService.findCurrentAnchor(Integer.parseInt(req.getRoom()));
            if (hId == liveUser.getId()) {
                user.setRole("anchor");
            } else {
                user.setRole("user");
            }
        } else {
            user.setRole("user");
        }
        try {
            String json = LiveChatUtil.sendMsg(req, user, null, ChatMsgTypeEnum.NORMAL.getCode());
            ChatLog log = new ChatLog(user.getUserId(), json, req.getToken(), new Date(),req.getRoom());
            chatRoomService.save(log);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceRuntimeException(CodeMsg.E_90001);
        }
        return Result.success(1);
    }

    /**
     * 发送系统消息：欢迎指令
     * 主播登录自己房间不需要发送欢迎指令
     *
     * @return
     */
    @ApiOperation(value = "欢迎指令", notes = "欢迎指令", response = Result.class)
    @PostMapping("/msg/system")
    public Result msgSystem(@RequestBody @ApiParam("欢迎指令") BaseMsgReq req) {
        // 获取登录用户
        LiveUser liveUser = getHeaderLiveUser();
        if (liveUser == null) {
            return Result.error(CodeMsg.E_20001);
        }
        try {
            ChatUser user = new ChatUser();
            user.setNickName(liveUser.getNickname());
            user.setUserId(liveUser.getId());
            String json = LiveChatUtil.sendMsg(req, user, null, ChatMsgTypeEnum.WELCOME.getCode());
            ChatLog log = new ChatLog(user.getUserId(), json, req.getToken(), new Date(),req.getRoom());
            chatRoomService.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(1);
    }

    /**
     * 过滤敏感词
     * @param sensitiveWord
     * @param roomId
     * @return
     */
    private String excludeKeyword(String sensitiveWord, Integer roomId) {
        System.out.println("敏感词过滤前：" + sensitiveWord);
        // 平台
        List<Keyword> keywords = keywordService.getUsableKeywords();
        if (keywords != null && keywords.size() > 0) {
            for (Keyword keyword : keywords) {
                if (sensitiveWord.contains(keyword.getKwName())) {
                    sensitiveWord = sensitiveWord.replace(keyword.getKwName(), keyword.getRpContent());
                }
            }
        }
        // 主播
        List<KeywordShield> shield = keywordShieldService.shield(roomId);
        if (shield != null && shield.size() > 0) {
            for (KeywordShield s : shield) {
                if (sensitiveWord.contains(s.getKeyword())) {
                    sensitiveWord = sensitiveWord.replace(s.getKeyword(), "*");
                }
            }
        }
        System.out.println("敏感词过滤后》》》" + sensitiveWord);
        return sensitiveWord;
    }
}
