package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.RoomManger;
import com.bh.live.pojo.res.user.RoomMangerRes;

import java.util.List;

/**
 * <p>
 * 直播间管理员表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
public interface IRoomMangerService extends IService<RoomManger> {

    /**
     * 获取管理员列表
     * @return
     */
    List<RoomMangerRes> getRoomManagers(Integer hostId);

    /**
     * 添加直播间管理员
     * @param nickname
     * @return
     */
    Result addLiveRoomManager(String nickname,Integer currentUser);

    /**
     * 撤销管理员
     * @param id
     * @return
     */
    Result cancelRoomManager(Integer id);

    /**
     * 特权管理
     * @param userId
     * @return
     */
    Result privilegeManager(Integer userId);

    /**
     * 用户辞职
     * @param userId
     * @return
     */
    Result managerResign(Integer userId);

}
