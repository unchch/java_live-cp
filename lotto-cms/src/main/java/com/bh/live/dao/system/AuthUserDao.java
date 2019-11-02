package com.bh.live.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.pojo.req.cms.SearchParamsReq;
import com.bh.live.pojo.res.cms.AuthUserSimpleRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
public interface AuthUserDao extends BaseMapper<AuthUser> {

    /**
     * 查询用户列表
     *
     * @param page
     * @param req
     * @return
     */
    List<AuthUser> selectAuthUserPage(@Param("page") Page<AuthUser> page, @Param("req") SearchParamsReq req);

    /**
     * 查询用户角色
     *
     * @param authUser
     * @return
     */
    String selectUserRoles(AuthUser authUser);

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    int deleteUserByIds(@Param("ids") String[] ids);

    /**
     * 查询用户角色id
     *
     * @param authUser
     * @return
     */
    String selectUserRoleIds(AuthUser authUser);

    /**
     * 根据角色id查询用户列表
     *
     * @param page
     * @param roleId
     * @param req
     * @return
     */
    List<AuthUser> selectUserListByRoleId(@Param("page") Page<AuthUser> page,
                                          @Param("roleId") Integer roleId,
                                          @Param("req") SearchParamsReq req);

    /**
     * 根据角色id获取未授权的用户
     *
     * @param page
     * @param roleId
     * @param req
     * @return
     */
    List<AuthUser> selectUserListExtendByRoleId(@Param("page") Page<AuthUser> page,
                                                @Param("roleId") Integer roleId,
                                                @Param("req") SearchParamsReq req);

    /**
     * 查询简单用户list
     *
     * @return
     */
    List<AuthUserSimpleRes> selectAuthUserSimpleList();
}
