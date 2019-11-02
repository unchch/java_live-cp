package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.user.Attention;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.user.AttentionReq;
import com.bh.live.pojo.res.user.UserBaseInfoRes;
import com.bh.live.user.controller.user.UserCenterController;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 用户个人中心 Mapper 接口
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-31
 */
public interface UserCenterDao extends BaseMapper<UserBaseInfoRes> {
    /**
     * 查询用户基础信息
     * @param userId
     * @return
     */
    UserBaseInfoRes queryUserById(@Param("userId") Integer userId);

    /**
     * 修改用户信息:昵称或者密码
     * @param nickname
     * @return
     */
    int updatenickName(@Param("userId") Integer userId, @Param("nickname") String nickname);

    /**
     * 修改用户密码
     * @param userId
     * @param password
     * @return
     */
    int updatePassword(@Param("userId")Integer userId, @Param("password")String password);

    /**
     * 用户修改昵称次数
     * @param userId
     * @param count
     * @return
     */
    int updateEditNameCount(@Param("userId")Integer userId, @Param("count")Integer count);

    /**
     * 查询用户昵称是否已经存在
     * @param nickname
     * @param userId
     * @return
     */
    Integer checkNickName(@Param("nickname")String nickname,@Param("userId")Integer userId);

    /**
     * 根据指定用户查询详情
     * @param userId
     * @param targetId
     * @return
     */
    UserBaseInfoRes queryUserInfo(@Param("userId")Integer userId, @Param("targetId")Integer targetId);
}
