package com.bh.live.shiro.provider.impl;


import com.bh.live.model.cms.AuthUser;
import com.bh.live.service.system.IAuthUserService;
import com.bh.live.shiro.provider.IAccountProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据库用户密码账户提供者实现类
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Service
public class AccountProviderImpl implements IAccountProvider {

    @Autowired
    private IAuthUserService accountService;

    @Override
    public AuthUser selectByUid(String uid) {
        return accountService.selectByUid(uid);
    }

}
