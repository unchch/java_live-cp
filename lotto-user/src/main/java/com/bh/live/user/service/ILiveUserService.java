package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.model.anchor.StatUserIncome;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.user.LoginReq;
import com.bh.live.pojo.req.user.RegisterReq;
import com.bh.live.common.result.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 直播用户表 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-07-24
 */
public interface ILiveUserService extends IService<LiveUser> {

    /**
     * @param userReq
     * @return Result
     * @throws
     * @description 登录
     * @author WuLong
     * @date 2019/7/24 15:01
     */
    Result login(LoginReq userReq) throws ServiceRuntimeException;

    /**
     * @param registerReq
     * @return Result
     * @throws
     * @description 注册
     * @author WuLong
     * @date 2019/7/24 15:02
     */
    Result register(RegisterReq registerReq) throws ServiceRuntimeException;

    /**
     * @param nickname
     * @return Result
     * @throws ServiceRuntimeException
     * @description 根据用户昵称（唯一）获取用户信息
     * @author Morphon
     * @date 2019/8/1 17:40
     */
    LiveUser getLiveUserByNickname(String nickname) throws ServiceRuntimeException;


    /**
     * @param mobile
     * @return
     * @throws ServiceRuntimeException
     * @description 根据用户手机号码获取用户信息
     * @author Morphon
     * @date 2019/8/5 9:40
     */
    LiveUser getUserByMobile(String mobile) throws ServiceRuntimeException;

}
