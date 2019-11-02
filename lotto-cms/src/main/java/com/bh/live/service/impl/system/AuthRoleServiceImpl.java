package com.bh.live.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.CmsRedisKey;
import com.bh.live.common.core.text.Convert;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.dao.system.AuthRoleDao;
import com.bh.live.dao.system.AuthRoleResourceDao;
import com.bh.live.dao.system.AuthUserRoleDao;
import com.bh.live.model.cms.AuthResource;
import com.bh.live.model.cms.AuthRole;
import com.bh.live.model.cms.AuthRoleResource;
import com.bh.live.model.cms.AuthUserRole;
import com.bh.live.service.system.IAuthResourceService;
import com.bh.live.service.system.IAuthRoleResourceService;
import com.bh.live.service.system.IAuthRoleService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Service
@Slf4j
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleDao, AuthRole> implements IAuthRoleService {

    @Resource
    private AuthRoleResourceDao authRoleResourceDao;

    @Autowired
    private IAuthResourceService authResourceService;

    @Autowired
    private IAuthRoleResourceService authRoleResourceService;

    @Resource
    private AuthRoleDao authRoleDao;

    @Resource
    private AuthUserRoleDao authUserRoleDao;

    @Autowired
    private RedisUtil redisManager;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updateRoleResources(Integer roleId, String idsStr) {
        //删除之前该角色的所有权限配置
        AuthRoleResource param = new AuthRoleResource();
        param.setRoleId(roleId);
        QueryWrapper<AuthRoleResource> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.lambda().eq(AuthRoleResource::getRoleId, roleId);
        authRoleResourceDao.delete(deleteWrapper);

        //然后给角色添加权限
        List<Integer> ids = Lists.newArrayList(Convert.toIntArray(idsStr));

        // 获取公共权限
        List<AuthResource> authResources = authResourceService.selectAuthCommonRes();
        if (CommonUtil.isNotEmpty(authResources)) {
            List<Integer> commonResIds = authResources.stream().map(AuthResource::getId).collect(Collectors.toList());
            ids.addAll(commonResIds);
        }

        List<AuthRoleResource> res = new ArrayList<>(ids.size());
        ids.forEach((id) -> {
            AuthRoleResource authRoleResource = new AuthRoleResource();
            authRoleResource.setRoleId(roleId);
            authRoleResource.setResourceId(id);
            res.add(authRoleResource);
        });
        //修改完后还要刷新过滤器
        if (authRoleResourceService.saveBatch(res)) {
            // 清楚缓存
            String redisCacheKey = CmsRedisKey.authRoleResourceCacheKey(roleId);
            redisManager.del(redisCacheKey);
            return Boolean.TRUE;
        }
        throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1102);
    }

    @Override
    public boolean checkUnique(String code, String name) {
        QueryWrapper<AuthRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AuthRole::getCode, code)
                .eq(AuthRole::getName, name);
        return CommonUtil.isEmpty(super.getOne(wrapper, false)) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public AuthRole selectRoleById(Integer id) {
        return authRoleDao.selectById(id);
    }

    @Override
    public boolean addRole(AuthRole role) throws DataAccessException {
        return authRoleDao.insert(role) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean updateRole(AuthRole role) throws DataAccessException {
        return authRoleDao.updateById(role) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteByRoleId(Integer roleId) throws DataAccessException {
        // 先判断是否有用户在使用该角色
        QueryWrapper<AuthUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AuthUserRole::getRoleId, roleId);
        List<AuthUserRole> authUserRoles = authUserRoleDao.selectList(queryWrapper);
        if (CommonUtil.isNotEmpty(authUserRoles)) {
            log.error("deleteByRoleId. 该角色下还有其他用户. roleId:{}, authUserRoles:{}", roleId, authUserRoles);
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1101);
        }

        // 可以进行删除
        if (authRoleDao.deleteById(roleId) > 0) {
            //删除角色后需要删除角色对应的权限
            QueryWrapper<AuthRoleResource> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.lambda().eq(AuthRoleResource::getRoleId, roleId);
            if (authRoleResourceDao.delete(deleteWrapper) >= 0) {
                return Boolean.TRUE;
            }
        }
        log.info("deleteByRoleId exception. roleId:{}", roleId);
        return Boolean.FALSE;
    }

    @Override
    public List<AuthRole> selectRoleList() throws DataAccessException {
        return authRoleDao.selectList(null);
    }
}
