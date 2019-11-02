package com.bh.live.shiro.matcher;

import com.bh.live.common.utils.security.GoogleAuthenticatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * 密码比较器
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Component
@Slf4j
public class PasswordMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String tokenCredentials = authenticationToken.getCredentials().toString();
        String infoCredentials = authenticationInfo.getCredentials().toString();

        String savedSecret = infoCredentials.split("\\:")[1];
        long code = Long.valueOf(tokenCredentials.split("\\:")[1]);
        long t = System.currentTimeMillis();
        GoogleAuthenticatorUtil ga = new GoogleAuthenticatorUtil();
        ga.setWindowSize(1);
        boolean google = ga.check_code(savedSecret, code, t);

        boolean principal = authenticationToken.getPrincipal().toString().equals(authenticationInfo.getPrincipals().getPrimaryPrincipal().toString());
        boolean credentials = tokenCredentials.split("\\:")[0].equals(infoCredentials.split("\\:")[0]);

        log.info("doCredentialsMatch. principal:{} credentials:{} google:{}", principal, credentials, google);
        return principal && credentials && google;
    }

}
