package com.bh.live.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.system.AuthRoleResourceDao;
import com.bh.live.model.cms.AuthRoleResource;
import com.bh.live.service.system.IAuthRoleResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源角色关联表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Service
public class AuthRoleResourceServiceImpl extends ServiceImpl<AuthRoleResourceDao, AuthRoleResource> implements IAuthRoleResourceService {

}
