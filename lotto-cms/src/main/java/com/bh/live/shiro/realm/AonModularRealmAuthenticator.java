package com.bh.live.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * realm验证
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class AonModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        assertRealmsConfigured();
        List<Realm> realms = this.getRealms()
                .stream()
                .filter(realm -> {
                    return realm.supports(authenticationToken);
                })
                .collect(toList());
        return realms.size() == 1 ?
                this.doSingleRealmAuthentication(realms.iterator().next(), authenticationToken)
                : this.doMultiRealmAuthentication(realms, authenticationToken);
    }

}
