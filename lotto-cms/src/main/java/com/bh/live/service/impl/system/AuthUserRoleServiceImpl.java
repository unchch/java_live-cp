package com.bh.live.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.system.AuthUserRoleDao;
import com.bh.live.model.cms.AuthUserRole;
import com.bh.live.service.system.IAuthUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Service
public class AuthUserRoleServiceImpl extends ServiceImpl<AuthUserRoleDao, AuthUserRole> implements IAuthUserRoleService {

}
