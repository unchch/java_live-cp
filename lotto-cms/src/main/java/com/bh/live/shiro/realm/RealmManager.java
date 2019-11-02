package com.bh.live.shiro.realm;

import com.bh.live.common.utils.RedisUtil;
import com.bh.live.shiro.matcher.JwtMatcher;
import com.bh.live.shiro.matcher.PasswordMatcher;
import com.bh.live.shiro.provider.IAccountProvider;
import com.bh.live.shiro.token.JwtToken;
import com.bh.live.shiro.token.PasswordToken;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * realm管理器
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Component
public class RealmManager {

    @Autowired
    private PasswordMatcher passwordMatcher;

    @Autowired
    private JwtMatcher jwtMatcher;

    @Autowired
    private IAccountProvider accountProvider;

    @Autowired
    private RedisUtil redisManager;

    public List<Realm> initGetRealm() {
        List<Realm> realmList = new LinkedList<>();
        // ----- password
        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setAccountProvider(accountProvider);
        passwordRealm.setCredentialsMatcher(passwordMatcher);
        passwordRealm.setAuthenticationTokenClass(PasswordToken.class);
        realmList.add(passwordRealm);
        // ----- jwt
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setRedisManager(redisManager);
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        jwtRealm.setAuthenticationTokenClass(JwtToken.class);
        realmList.add(jwtRealm);
        return Collections.unmodifiableList(realmList);
    }

}
