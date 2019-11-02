package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.pojo.res.user.UserBaseInfoRes;
import com.bh.live.user.dao.UserCenterDao;
import com.bh.live.user.service.IUserCenterService;
import com.bh.live.user.utils.MD5Utils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Description: 用户中心 实现类
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/31 10:50
 */
@Service
public class UserCenterServiceImpl extends ServiceImpl<UserCenterDao, UserBaseInfoRes> implements IUserCenterService {

    @Resource
    private UserCenterDao userCenterDao;

    @Override
    public UserBaseInfoRes queryUserById(Integer userId) throws ServiceRuntimeException {
        return userCenterDao.queryUserById(userId);
    }

    @Override
    public UserBaseInfoRes queryUserInfo(Integer userId, Integer targetId) throws ServiceRuntimeException {
        return userCenterDao.queryUserInfo(userId,targetId);
    }

    @Override
    public int updateNickname(Integer userId, String nickname, Integer count) throws ServiceRuntimeException {
        // 值只允许修改一次
        if (count>0){
            throw new ServiceRuntimeException(CodeMsg.E_10028);
        }
        Integer result =-1;
        // 先查询昵称是否存在
        result=userCenterDao.checkNickName(nickname,userId);
        if (result==0){
            if (count ==0){
                // 用户修改昵称次数
                count++;
                result = userCenterDao.updateEditNameCount(userId,count);
            }
            if (result>0) {
                result = userCenterDao.updatenickName(userId, nickname);
            }
        }else {
            // 昵称已经存在
            throw new ServiceRuntimeException(CodeMsg.E_10027);
        }
        return result;
    }

    @Override
    public int updatePassword(Integer userId, String password) {
        // 加密
        return userCenterDao.updatePassword(userId, MD5Utils.encryptPassword(password));
    }
}
