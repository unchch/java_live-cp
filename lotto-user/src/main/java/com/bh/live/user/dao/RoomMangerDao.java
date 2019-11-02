package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.user.RoomManger;
import com.bh.live.pojo.res.user.RoomMangerRes;
import com.bh.live.pojo.res.user.RoomUserMangerRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 直播间管理员表 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
public interface RoomMangerDao extends BaseMapper<RoomManger> {

    /**
     * 根据主播id查询房间管理员列表
     * @return
     */
    List<RoomMangerRes> queryRoomManagerList(@Param("hostId") Integer hostId);

    /**
     * 根据当前登录用户查询特权管理
     * @return
     */
    List<RoomUserMangerRes> queryPrivilegeList(@Param("userId") Integer userId);
}
