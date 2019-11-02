package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.model.user.Attention;
import com.bh.live.pojo.req.user.AttentionReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.user.UserBaseInfoRes;

import java.math.BigDecimal;

/**
 * <p>
 * 用户中心 服务类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-31
 */
public interface IUserCenterService extends IService<UserBaseInfoRes> {

    /**
     * 根据用户Id查询用户信息
     */
    UserBaseInfoRes queryUserById(Integer userId) throws ServiceRuntimeException;

    /**
     * 查询指定用户的详情
     */
    UserBaseInfoRes queryUserInfo(Integer userId,Integer targetId) throws ServiceRuntimeException;

    /**
     * 修改用户信息:昵称
     * @param nickname
     * @return
     */
    int updateNickname(Integer userId, String nickname, Integer count)throws ServiceRuntimeException;

    /**
     * 修改用户密码
     * @param userId
     * @param password
     * @return
     */
    int updatePassword(Integer userId, String password);
}
