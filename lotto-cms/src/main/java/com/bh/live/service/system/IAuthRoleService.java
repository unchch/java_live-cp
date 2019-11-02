package com.bh.live.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.cms.AuthRole;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
public interface IAuthRoleService extends IService<AuthRole> {
    /**
     * 修改角色权限配置
     *
     * @param roleId
     * @param ids
     * @return
     */
    boolean updateRoleResources(Integer roleId, String ids);

    /**
     * 检查角色编码和角色名称是否重复
     * @param code
     * @param name
     * @return
     */
    boolean checkUnique(String code, String name);

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    AuthRole selectRoleById(Integer id);

    /**
     * description 添加角色
     *
     * @param role 1
     * @return boolean
     */
    boolean addRole(AuthRole role);

    /**
     * description 修改角色
     *
     * @param role 1
     * @return boolean
     */
    boolean updateRole(AuthRole role);

    /**
     * description 根据id删除角色
     *
     * @param roleId 1
     * @return boolean
     */
    boolean deleteByRoleId(Integer roleId);

    /**
     * description 获取角色列表
     *
     * @return java.util.List<com.usthe.bootshiro.domain.bo.AuthRole>
     */
    List<AuthRole> selectRoleList();
}
