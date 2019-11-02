package com.bh.live.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.model.cms.AuthUserRole;
import com.bh.live.pojo.req.cms.SearchParamsReq;
import com.bh.live.pojo.res.cms.AuthUserSimpleRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
public interface IAuthUserService extends IService<AuthUser> {

    /**
     * 查询简单用户对象
     * @return
     */
    List<AuthUserSimpleRes> selectAuthUserSimpleList();

    /**
     * 获取用户列表
     *
     * @param req
     * @return
     */
    TableDataInfo selectUserList(SearchParamsReq req);

    /**
     * 根据uid获取用户
     *
     * @param uid
     * @return
     */
    AuthUser selectByUid(String uid);

    /**
     * 添加用户
     *
     * @param authUser
     * @return
     */
    boolean addUser(AuthUser authUser);

    /**
     * 加载用户角色
     *
     * @param authUser
     * @return
     */
    String loadAccountRole(AuthUser authUser);

    /**
     * 检查账户是否唯一
     *
     * @param uid
     * @return
     */
    boolean checkUnique(String uid);


    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    boolean updateUser(AuthUser user);

    /**
     * 更新用户角色
     *
     * @param uid
     * @param roles
     * @return
     */
    boolean updateUserRole(String uid, Set<Integer> roles);

    /**
     * 删除用户角色
     *
     * @param uid
     * @return
     */
    boolean deleteUserRole(String uid);

    /**
     * 添加用户角色
     *
     * @param uid
     * @param roles
     * @return
     */
    boolean addUserRole(String uid, Set<Integer> roles);

    /**
     * 删除用户
     *
     * @param uid
     * @return
     */
    boolean deleteUserById(String uid);

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    boolean deleteUserByIds(String ids);


    /**
     * 批量授权用户角色
     *
     * @param authUserRoleList
     * @return
     */
    boolean authorityUserRoleBatch(List<AuthUserRole> authUserRoleList);

    /**
     * 批量删除用户授权
     * @param authUserRoleList
     * @return
     */
    boolean batchDeleteAuthorityUserRole(List<AuthUserRole> authUserRoleList);

    /**
     * 查询用户角色id
     *
     * @param authUser
     * @return
     */
    String selectUserRoleIds(AuthUser authUser);

    /**
     * 生成google验证密匙
     *
     * @param uid
     * @return
     */
    boolean generateSecretKey(String uid);

    /**
     * 根据角色id查询用户列表
     *
     * @param roleId
     * @param req
     * @return
     */
    TableDataInfo selectUserListByRoleId(Integer roleId, SearchParamsReq req);

    /**
     * 根据角色id获取未授权的用户
     *
     * @param roleId
     * @param req
     * @return
     */
    TableDataInfo selectUserListExtendByRoleId(Integer roleId, SearchParamsReq req);
}
