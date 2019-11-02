package com.bh.live.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bh.live.common.config.JwtCmsProperties;
import com.bh.live.common.config.JwtProperties;
import com.bh.live.common.constant.CommonConstants;
import com.bh.live.model.user.LiveUser;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtHelper {
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    JwtCmsProperties jwtCmsProperties;
    
    public static void main(String[] args) {
    	JWTCreator.Builder builder = JWT.create();
	}

    /**
     *@description: 生成token的操作
     *@author: WuLong
     *@date: 2019/7/19 10:29
     *@param: Map<String, String>
     *@return:  String
     *@exception:
     */
    public String genToken(Map<String, String> claims){
        return handleToken(claims, jwtProperties.getSecret(), jwtProperties.getIssuer());
    }

    /**
     *@description: 生成token的操作
     *@author: WuLong
     *@date: 2019/7/19 10:29
     *@param: Map<String, String>
     *@return:  String
     *@exception:
     */
    public String genCmsToken(Map<String, String> claims){
        return handleToken(claims, jwtCmsProperties.getSecret(), jwtCmsProperties.getIssuer());
    }

    private String handleToken(Map<String, String> claims, String secret, String issuer) {
        try {
            //签名算法
            Algorithm algorithm = Algorithm.HMAC256(secret);

            JWTCreator.Builder builder = JWT.create().withIssuer(issuer);
            //相当于将claims存储在token中
            claims.forEach((k, v) -> builder.withClaim(k, v));
            return builder.sign(algorithm).toString();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *@description: 验证token
     *@author: WuLong
     *@date: 2019/7/19 10:29
     *@param:  String
     *@return:   Map<String, String>
     *@exception:
     */
    public Map<String, String> verifyToken(String token)  {
        return handleVerifyToken(token, jwtProperties.getSecret(), jwtProperties.getIssuer());
    }

    /**
     *@description: 验证token
     *@author: WuLong
     *@date: 2019/7/19 10:29
     *@param:  String
     *@return:   Map<String, String>
     *@exception:
     */
    public Map<String, String> verifyCmsToken(String token)  {
        return handleVerifyToken(token, jwtCmsProperties.getSecret(), jwtCmsProperties.getIssuer());
    }

    private Map<String, String> handleVerifyToken(String token, String secret, String issuer) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> map = jwt.getClaims();
        Map<String, String> resultMap = Maps.newHashMap();
        map.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

    /**
     * @param user
     * @param auto 一周内自动登录 0否 1是
     * @return String  token
     * @description 生成token
     * @author WuLong
     * @date 2019/7/25 14:43
     */
    public String getTokenValue(LiveUser user, Integer auto ,String userAgent) {
        Map<String, String> c = new LinkedHashMap<>();
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_ID, String.valueOf(user.getId()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_MOBILE, user.getMobile());
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_USABLE, String.valueOf(user.getIsUsable()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_ONLINE, String.valueOf(user.getIsOnline()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_PUBLISH, String.valueOf(user.getIsPublish()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_SPEAK, String.valueOf(user.getIsSpeak()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_LOGIN, String.valueOf(user.getIsLogin()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_NICK_NAME, String.valueOf(user.getNickname()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_AUTO, String.valueOf(auto));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_EXPERT,String.valueOf(user.getIsExpert()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_ANCHOR,String.valueOf(user.getIsAnchor()));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IP,user.getLastip());
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_DEVICE,user.getTerminal());
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_USER_AGENT,userAgent);
        return genToken(c);
    }
}
