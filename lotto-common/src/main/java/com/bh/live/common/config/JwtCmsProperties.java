package com.bh.live.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "cms.jwt")
public class JwtCmsProperties {
    private String secret;

    private String issuer;

    private long expireTime;

}
