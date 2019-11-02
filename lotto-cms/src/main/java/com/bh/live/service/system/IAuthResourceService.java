package com.bh.live.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.cms.AuthResource;
import com.bh.live.model.cms.AuthRoleResource;
import com.bh.live.model.cms.AuthUser;

import java.util.List;

/**
 * <p>
 * 资源信息表(菜单,资源),此表所有子站及主站共享 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
public interface IAuthResourceService extends IService<AuthResource> {
    /**
     * 根据uid获取用户菜单
     *
     * @param authUser
     * @return
     */
    List<AuthResource> getAuthorityMenusByUid(AuthUser authUser);

    /**
     * 根据uid获取用户菜单
     *
     * @param authUser
     * @return
     */
    List<AuthResource> getAuthorityApisByUid(AuthUser authUser);


    /**
     * 获取所有资源权限
     *
     * @return
     */
    List<AuthResource> getAll();

    /**
     * 通过角色id获取已授权的api
     *
     * @param authRoleResource
     * @return
     */
    List<AuthResource> getAuthorityApisNotParentByRoleId(AuthRoleResource authRoleResource);

    /**
     * 获取公共菜单资源
     *
     * @return
     */
    List<AuthResource> selectAuthCommonRes();
}
