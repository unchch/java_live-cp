package com.bh.live.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.cms.AuthResource;
import com.bh.live.model.cms.AuthRoleResource;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.shiro.dto.RolePermRule;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * <p>
 * 资源信息表(菜单,资源),此表所有子站及主站共享 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
public interface AuthResourceDao extends BaseMapper<AuthResource> {

    /**
     * 获取所有权限
     *
     * @return
     */
    List<RolePermRule> selectRoleRules();

    /**
     * 根据Uid获取授权菜单
     *
     * @param authUser
     * @return
     * @throws DataAccessException
     */
    List<AuthResource> selectAuthorityMenusByUid(AuthUser authUser) throws DataAccessException;

    /**
     * 根据Uid获取授权菜单
     *
     * @param authUser
     * @return
     * @throws DataAccessException
     */
    List<AuthResource> selectAuthorityApisByUid(AuthUser authUser) throws DataAccessException;

    /**
     * 根据角色id获取res(不包括菜单和目录)列表
     *
     * @param authRoleResource
     * @return
     * @throws DataAccessException
     */
    List<AuthResource> selectApisNotParentByRoleId(AuthRoleResource authRoleResource) throws DataAccessException;
}
