package com.bh.live.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "access.jwt.token")
public class JwtProperties {
    private String secret;

    private String issuer;

    private long expireTime;

    private long weekExpireTime;
}
