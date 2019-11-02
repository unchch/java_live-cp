package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.user.ChatLog;
import com.bh.live.pojo.req.live.ChatLogReq;
import com.bh.live.user.dao.ChatRoomDao;
import com.bh.live.user.service.ChatRoomService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 聊天室管理
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/7 10:20
 */
@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomDao, ChatLog> implements ChatRoomService {

    @Resource
    private ChatRoomDao chatRoomDao;


    @Override
    public Integer isNoTalk(Integer hostId, Integer userId) {
        Integer channel = chatRoomDao.isNoTalkForChannel(userId);
        if (channel==0){
            return chatRoomDao.isNoTalk(hostId, userId);
        }
        return channel;
    }

    @Override
    public List<ChatLog> getPage(ChatLogReq req) {
        return chatRoomDao.getPage(req);
    }

    @Override
    public Integer findManager(Integer roomId,Integer id) {
        return chatRoomDao.findManager(roomId,id);
    }

    @Override
    public Integer findCurrentAnchor(Integer roomId) {
        return  chatRoomDao.findCurrentAnchor(roomId);
    }

    @Override
    public List<HostRoom> findLivingRoom(String seedNo) {
        return chatRoomDao.findLivingRoom(seedNo);
    }
}
