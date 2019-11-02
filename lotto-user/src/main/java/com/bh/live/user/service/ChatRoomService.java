package com.bh.live.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.user.ChatLog;
import com.bh.live.pojo.req.live.ChatLogReq;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;


/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/7 10:19
 */
public interface ChatRoomService extends IService<ChatLog> {

    /**
     * 根据用户查询是否并禁言
     * @param hostId
     * @param userId
     * @return
     */
    Integer isNoTalk(Integer hostId, Integer userId);

    /**
     * 获取分页历史
     * @param req
     * @return
     */
    List<ChatLog> getPage(ChatLogReq req);
    /**
     * 判断是否是房管
     * @param id
     * @return
     */
    Integer findManager(Integer roomId,Integer id);

    /**
     * 获取本场主播
     * @param roomId
     * @return
     */
    Integer findCurrentAnchor(Integer roomId);

    /**
     * 获取彩种下所有正在直播的房间
     * @param seedNo
     * @return
     */
    List<HostRoom> findLivingRoom(String seedNo);
}
