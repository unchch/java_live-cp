package com.bh.live.user.dao; /**
 * @Description: $discription
 * @Author: Dingo
 * @Version: 1.0
 * @date: 2019/8/7 11:53
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.user.ChatLog;
import com.bh.live.pojo.req.live.ChatLogReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: wuhuanrong
 * @Date: 2019/8/7 11:53
 * @Description:
 */
public interface ChatRoomDao extends BaseMapper<ChatLog> {

    Integer isNoTalk(@Param("hostId") Integer hostId, @Param("userId") Integer userId);

    List<ChatLog> getPage( @Param("req") ChatLogReq req);

    Integer findManager(@Param("roomId")Integer roomId, @Param("userId")Integer id);

    Integer findCurrentAnchor(@Param("roomId")Integer roomId);

    List<HostRoom> findLivingRoom(@Param("seedNo")String seedNo);

    Integer isNoTalkForChannel( @Param("userId") Integer userId);

}
