package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.model.user.RoomManger;
import com.bh.live.pojo.res.anchor.HostUserFormRes;
import com.bh.live.pojo.res.user.RoomMangerRes;
import com.bh.live.pojo.res.user.RoomUserMangerRes;
import com.bh.live.user.dao.RoomMangerDao;
import com.bh.live.user.service.IHostUserService;
import com.bh.live.user.service.ILiveUserService;
import com.bh.live.user.service.IRoomMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 直播间管理员表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Service
public class RoomMangerServiceImpl extends ServiceImpl<RoomMangerDao, RoomManger> implements IRoomMangerService {

    @Resource
    private RoomMangerDao roomMangerDao;

    @Autowired
    private ILiveUserService liveUserService;

    @Autowired
    private IHostUserService hostUserService;

    @Override
    public List<RoomMangerRes> getRoomManagers(Integer hostId) {
        return roomMangerDao.queryRoomManagerList(hostId);
    }

    @Override
    public Result addLiveRoomManager(String nickname, Integer currentUser) {
        LiveUser user = null;
        try {
            user = liveUserService.getLiveUserByNickname(nickname);
        } catch (Exception e) {
            return Result.error(CodeMsg.E_500);
        }
        if (user == null) {
            return Result.error(CodeMsg.E_10010);
        }
        //获取主播信息
        HostUserFormRes hostUser = hostUserService.getHostUserById(currentUser);

        RoomManger manger = new RoomManger();
        manger.setHostUserId(currentUser);
        manger.setUserLv(user.getUserGrade());
        manger.setRoomId(hostUser.getRoomId());
        manger.setUserId(user.getId());

        int insert = roomMangerDao.insert(manger);
        if (insert > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.E_500);
        }
    }

    @Override
    public Result cancelRoomManager(Integer id) {
        RoomManger manger = new RoomManger();
        manger.setId(id);
        manger.setIsUsable(0);
        int i = roomMangerDao.updateById(manger);
        if (i > 0) return Result.success();
        return Result.error(CodeMsg.E_500);
    }

    @Override
    public Result privilegeManager(Integer userId) {
        List<RoomUserMangerRes> roomUserMangers = roomMangerDao.queryPrivilegeList(userId);
        return Result.success(roomUserMangers);
    }

    @Override
    public Result managerResign(Integer id) {
        RoomManger manger = new RoomManger();
        manger.setId(id);
        manger.setIsUsable(0);
        boolean update = updateById(manger);
        if (update) {
            return Result.success();
        }
        return Result.error(CodeMsg.E_500);
    }


}
