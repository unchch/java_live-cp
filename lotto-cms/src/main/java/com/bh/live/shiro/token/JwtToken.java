package com.bh.live.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT token
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Getter
@Setter
@AllArgsConstructor
public class JwtToken implements AuthenticationToken{

    /**
     * 用户的标识
     */
    private String uid;
    /**
     * 用户的IP
     */
    private String ipHost;

    /**
     * json web token值
     */
    private String jwt;

    /**
     * 设备信息
     */
    private String deviceInfo;

    @Override
    public Object getPrincipal() {
        return this.uid;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }

}
