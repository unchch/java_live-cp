package com.bh.live.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.cms.AuthUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
public interface AuthUserRoleDao extends BaseMapper<AuthUserRole> {
    /**
     * 批量删除用户和角色关联
     *
     * @param ids
     * @param roleId
     * @return
     */
    int deleteByUserIds(@Param("ids") List<String> ids, @Param("roleId") Integer roleId);
}
