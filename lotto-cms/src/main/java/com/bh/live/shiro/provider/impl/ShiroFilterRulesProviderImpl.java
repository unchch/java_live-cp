package com.bh.live.shiro.provider.impl;

import com.bh.live.dao.system.AuthResourceDao;
import com.bh.live.shiro.dto.RolePermRule;
import com.bh.live.shiro.provider.IShiroFilterRulesProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 动态过滤规则提供者实现类
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Service
public class ShiroFilterRulesProviderImpl implements IShiroFilterRulesProvider {

    @Resource
    private AuthResourceDao authResourceDao;

    /**
     * 获取所有权限所赋予的角色
     * @return
     */
    @Override
    public List<RolePermRule> loadRolePermRules() {
        return authResourceDao.selectRoleRules();
    }
}