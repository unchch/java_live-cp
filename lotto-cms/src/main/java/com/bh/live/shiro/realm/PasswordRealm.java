package com.bh.live.shiro.realm;


import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.security.ShiroMd5Util;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.shiro.provider.IAccountProvider;
import com.bh.live.shiro.token.PasswordToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 登录认证realm
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class PasswordRealm extends AuthorizingRealm {

    private IAccountProvider accountProvider;

    public void setAccountProvider(IAccountProvider accountProvider) {
        this.accountProvider = accountProvider;
    }

    /**
     * description 此Realm只支持PasswordToken
     *
     * @return java.lang.Class<?>
     */
    @Override
    public Class<?> getAuthenticationTokenClass() {
        return PasswordToken.class;
    }

    /**
     * description 这里只需要认证登录，成功之后派发 json web token 授权在那里进行
     *
     * @param principalCollection 1
     * @return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!(authenticationToken instanceof PasswordToken)) {
            return null;
        }

        if (null == authenticationToken.getPrincipal() || null == authenticationToken.getCredentials()) {
            throw new UnknownAccountException();
        }

        String uid = String.valueOf(authenticationToken.getPrincipal());
        String credentials = String.valueOf(authenticationToken.getCredentials());
        AuthUser authUser = accountProvider.selectByUid(uid);
        if (CommonUtil.isNotEmpty(authUser)) {
            //账户状态(1.正常 2.锁定 3.删除 4.非法)
            if (authUser.getStatus().compareTo(LotteryConstants.VALUE_1) != 0) {
                throw new DisabledAccountException();
            }
            ((PasswordToken) authenticationToken).setPassword(ShiroMd5Util.genMd5Pass(credentials.split("\\:")[0], authUser.getSalt()));
            ((PasswordToken) authenticationToken).setQrcode(credentials.split("\\:")[1]);
            // 用盐对密码进行MD5加密
            return new SimpleAuthenticationInfo(
                    uid,
                    String.format("%s:%s", authUser.getPassword(), authUser.getSecretKey()),
                    getName()) {
            };
        } else {
            return new SimpleAuthenticationInfo(uid, "", getName());
        }
    }
}
