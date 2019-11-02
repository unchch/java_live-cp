package com.bh.live.user.provider;

import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.enums.ChatMsgTypeEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.user.ChatLog;
import com.bh.live.pojo.req.live.MsgRcpReq;
import com.bh.live.pojo.res.live.BaseData;
import com.bh.live.pojo.res.live.ChatGuess;
import com.bh.live.pojo.res.live.ChatOpenCode;
import com.bh.live.user.service.ChatRoomService;
import com.bh.live.user.utils.live.LiveChatUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Description: 直播间消息指令发送
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/15 11:33
 */
@RestController
@RequestMapping("/chat-msg")
@Slf4j
public class ChatMsgSendController {

    @Autowired
    ChatRoomService chatRoomService;
    /**
     * 发送消息
     *
     * @return
     */
    @ApiOperation(value = "发送消息", notes = "发送消息", response = Result.class)
    @PostMapping(value = "/send", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Integer send(@RequestBody @ApiParam("消息对象") MsgRcpReq req) {
        try {
            BaseData data = new BaseData();
            data.setEvent("msg_system");
            CommonUtil.beanCopy(req, data);
            Object obj = req.getObj();
            if (req.getSysType() == ChatMsgTypeEnum.OPEN_CODE.getCode() || req.getSysType() == ChatMsgTypeEnum.CURRENT_OPEN.getCode()) {
                ChatOpenCode openCode = JSONObject.parseObject(JSONObject.toJSONString(obj), ChatOpenCode.class);
                data.setOpenCode(openCode);
            } else if (req.getSysType() == ChatMsgTypeEnum.GUESS.getCode()) {
                ChatGuess guess = JSONObject.parseObject(JSONObject.toJSONString(obj), ChatGuess.class);
                data.setGuess(guess);
                data.setUser(req.getUser());
            }
            // 查询当前彩种下正在直播的房间
            List<HostRoom> rooms = chatRoomService.findLivingRoom(req.getSeedNo());
            data.setSendTime(new Date());
            data.setSysType(req.getSysType());
            data.setToken("");
            data.setType("msg_system");
            data.setContent("");
            // 循环推送消息
            for(HostRoom room:rooms){
                data.setRoom(room.getId().toString());
                String json = LiveChatUtil.execute(data);
                ChatLog log = new ChatLog(null, json, req.getToken(), new Date(),room.getId().toString());
                chatRoomService.save(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceRuntimeException(CodeMsg.E_90004);
        }
        return 1;
    }

}
