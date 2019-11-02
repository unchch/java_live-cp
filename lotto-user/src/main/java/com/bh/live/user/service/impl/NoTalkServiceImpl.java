package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.anchor.NoTalk;
import com.bh.live.model.user.LiveUser;
import com.bh.live.user.dao.NoTalkDao;
import com.bh.live.user.service.ILiveUserService;
import com.bh.live.user.service.INoTalkService;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 直播间禁言表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Service
public class NoTalkServiceImpl extends ServiceImpl<NoTalkDao, NoTalk> implements INoTalkService {

    @Resource
    private NoTalkDao noTalkDao;

    @Autowired
    private ILiveUserService liveUserService;

    @Override
    public Result noTalkList(String nickname, String queryType, Integer hostUserId) {

        return Result.success(noTalkDao.getNoTalkList(nickname, queryType, hostUserId));
    }

    @Override
    public Result addNoTalk(String value, Integer time, String timeType, Integer type, String remark, LiveUser liveUser) {
        //处理禁言时间
        if (timeType.equals("day")) {
            time = time * 24 * 60;
        } else if (timeType.equals("hour")) {
            time = time * 60;
        }
        String endTime = DateUtils.addTime(DateUtils.formatDateTime(new Date()), time.toString());
        NoTalk noTalk = new NoTalk();
        noTalk.setOperater(liveUser.getNickname() == null ? "当前登录人" : liveUser.getNickname());
        noTalk.setBeginTime(new Date());
        noTalk.setHostUserId(liveUser.getId());
        noTalk.setEndTime(DateUtils.parseDate(endTime));
        noTalk.setType(type);
        noTalk.setRemark(remark);
        if (type == 1) {
            LiveUser user = liveUserService.getLiveUserByNickname(value);
            if (user == null) return Result.error(CodeMsg.E_10010);
            noTalk.setUserLevel(user.getUserGrade());
            noTalk.setUserId(user.getId());
        }
        if (type == 2) {
            noTalk.setNoTalkIp(value);
        }
        int insert = noTalkDao.insert(noTalk);
        if (insert > 0) return Result.success();

        return Result.error(CodeMsg.E_500);
    }

}
