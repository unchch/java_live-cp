package com.bh.live.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.CmsRedisKey;
import com.bh.live.common.constant.SystemConstants;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.JacksonUtil;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.dao.system.AuthResourceDao;
import com.bh.live.model.cms.AuthResource;
import com.bh.live.model.cms.AuthRoleResource;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.service.system.IAuthResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资源信息表(菜单,资源),此表所有子站及主站共享 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Service
@Slf4j
public class AuthResourceServiceImpl extends ServiceImpl<AuthResourceDao, AuthResource> implements IAuthResourceService {
    @Resource
    private AuthResourceDao authResourceDao;

    @Resource
    private RedisUtil redisManager;

    @Override
    public List<AuthResource> getAuthorityMenusByUid(AuthUser authUser) throws DataAccessException {
        return authResourceDao.selectAuthorityMenusByUid(authUser);
    }

    @Override
    public List<AuthResource> getAuthorityApisByUid(AuthUser authUser) {
        return authResourceDao.selectAuthorityApisByUid(authUser);
    }

    @Override
    public List<AuthResource> getAll() throws DataAccessException {
        return authResourceDao.selectList(null);
    }

    @Override
    public List<AuthResource> getAuthorityApisNotParentByRoleId(AuthRoleResource authRoleResource) throws DataAccessException {
        return authResourceDao.selectApisNotParentByRoleId(authRoleResource);
    }

    @Override
    public List<AuthResource> selectAuthCommonRes() {
        List<AuthResource> ret;
        Object cacheValue = redisManager.get(CmsRedisKey.AUTH_COMMON_RESOURCE);
        if (CommonUtil.isNotEmpty(cacheValue)) {
            try {
                ret = JacksonUtil.json2list(String.valueOf(cacheValue), AuthResource.class);
                if (CommonUtil.isNotEmpty(ret)) {
                    return ret;
                }
            } catch (Exception e) {
                log.error("selectAuthCommonRes get redis cache failed.");
            }
        }
        QueryWrapper<AuthResource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AuthResource::getType, SystemConstants.AUTH_COMMON_RES_TYPE);
        ret = baseMapper.selectList(wrapper);

        if (CommonUtil.isEmpty(ret)) {
            return ret;
        }
        try {
            redisManager.set(CmsRedisKey.AUTH_COMMON_RESOURCE, JacksonUtil.obj2json(ret));
        } catch (Exception e) {
            log.error("selectAuthCommonRes set redis cache failed.");
        }
        return ret;
    }
}
