package com.bh.live.pojo.res.cms;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JwtAccount账户
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Data
public class JwtAccountRes implements Serializable {

    /**
     * 令牌id
     */
    private String tokenId;
    /**
     * 客户标识（用户名、账号）
     */
    private String uid;
    /**
     * 签发者(JWT令牌此项有值)
     */
    private String issuer;
    /**
     * 签发时间
     */
    private Date issuedAt;
    /**
     * 接收方(JWT令牌此项有值)
     */
    private String audience;
    /**
     * 访问主张-角色(JWT令牌此项有值)
     */
    private String roles;
    /**
     * 访问主张-资源(JWT令牌此项有值)
     */
    private String perms;
    /**
     * 用户域ID
     */
    private Long domainId;
    /**
     * 客户地址
     */
    private String host;

}
