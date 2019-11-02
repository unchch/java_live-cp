package com.bh.live.shiro.matcher;

import com.bh.live.common.utils.token.JsonWebTokenUtil;
import com.bh.live.pojo.res.cms.JwtAccountRes;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * jwt比较器
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Component
public class JwtMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String jwt = (String) authenticationInfo.getCredentials();
        JwtAccountRes jwtAccount;
        try {
            jwtAccount = JsonWebTokenUtil.parseJwt(jwt, JsonWebTokenUtil.SECRET_KEY);
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            // 令牌错误
            throw new AuthenticationException("errJwt");
        } catch (ExpiredJwtException e) {
            // 令牌过期
            throw new AuthenticationException("expiredJwt");
        } catch (Exception e) {
            throw new AuthenticationException("errJwt");
        }
        if (null == jwtAccount) {
            throw new AuthenticationException("errJwt");
        }
        return true;
    }

}
