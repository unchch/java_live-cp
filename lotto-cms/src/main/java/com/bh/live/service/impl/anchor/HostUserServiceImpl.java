package com.bh.live.service.impl.anchor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.dao.anchor.HostUserDao;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.HostUserRes;
import com.bh.live.pojo.res.anchor.HostUserResDetail;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.anchor.IHostRoomService;
import com.bh.live.service.anchor.IHostUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 主播表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@Service
public class HostUserServiceImpl extends ServiceImpl<HostUserDao, HostUser> implements IHostUserService {

    @Autowired
    private IHostRoomService hostRoomService;

    @Override
    public Object getHostUserList(HostUserReq param, String callType) {
        Page<HostUserRes> page = new Page<>(param.getPageNum(), param.getPageSize());
        if (callType.equals("page")) {
            page.setRecords(baseMapper.getListByPage(page, param));
            return new TableDataInfo<>(page);
        }else {
            return baseMapper.getListByPage(null, param);
        }
    }

    @Override
    public HostUserResDetail getHostUserDetailByUserId(Integer userId) {
        return baseMapper.userDetail(userId);
    }

    @Override
    public HostUser getHostUserByUserId(Integer userId) {
        QueryWrapper<HostUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void updateHostUser(HostUserReq req) {
        HostUser hostUser = new HostUser();
        hostUser.setUpdateTime(new Date());
        BeanUtils.copyProperties(req, hostUser);
        baseMapper.updateById(hostUser);
    }

    @Override
    @Transactional
    public Result verifyHostUser(Integer id, Integer verifyStatus) {
        HostUser hostUser = getHostUserByUserId(id);
        if (hostUser == null) {
            return Result.error(CodeMsg.E_10006);
        }
        hostUser.setVerifyTime(new Date());
        hostUser.setUpdateTime(new Date());
        hostUser.setVerifyStatus(verifyStatus);
        //审核通过生成房间号(审核通过才能生成房间)
        if (verifyStatus == 1) {
            HostRoom room = new HostRoom();
            QueryWrapper<HostRoom> wrapper = new QueryWrapper<>();
            wrapper.eq("host_id", hostUser.getUserId());
            HostRoom hostRoom = hostRoomService.getOne(wrapper);
            if (hostRoom != null) {
                return Result.error("主播已存在房间号,请检查数据");
            }
            //生成房间id  年的后两位加6位随机数
            String roomId = DateUtils.parseDateToStr("yyyy", new Date()).substring(2, 4) + (int) ((Math.random() * 9 + 1) * 100000);
            room.setId(Integer.valueOf(roomId));
            room.setSecretKey(StringUtils.getRandomString(32)); //生成32位房间密钥
            room.setHostId(hostUser.getUserId());
            room.setNickname(hostUser.getUsername());
            boolean save = false;
            try {
                save = hostRoomService.save(room);
            } catch (Exception e) {
                if (e instanceof DuplicateKeyException) {
                    //如果报唯一主键约束，重新生成房间号
                    save = cycle(room);
                } else {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
            if (!save) {
                return Result.error(CodeMsg.E_500);
            } else {
                hostUser.setRoomId(room.getId());
                baseMapper.updateById(hostUser);
            }
        }
        return Result.success();
    }

    private boolean cycle(HostRoom room) {
        try {
            String roomId;
            roomId = DateUtils.parseDateToStr("yyyy", new Date()).substring(2, 4) + (int) ((Math.random() * 9 + 1) * 100000);
            room.setId(Integer.valueOf(roomId));
            return hostRoomService.save(room);
        } catch (DuplicateKeyException e1) {
            cycle(room);
        }
        return false;
    }

}
