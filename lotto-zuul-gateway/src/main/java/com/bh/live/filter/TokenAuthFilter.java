package com.bh.live.filter;

import com.bh.live.common.config.JwtProperties;
import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.constant.SymbolConstants;
import com.bh.live.common.exception.TokenCheckException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.JsonUtil;
import com.bh.live.common.utils.JwtHelper;
import com.bh.live.common.utils.RedisUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author WuLong
 * @date 2019/7/27 17:26
 * @description token鉴权过滤
 */
@Component
@Slf4j
public class TokenAuthFilter extends ZuulFilter {
    @Resource
    JwtHelper jwtHelper;

    @Autowired
    JwtProperties jwtProperties;

    @Resource
    RedisUtil redisUtil;
    /**
     * 排除过滤的 uri 地址
     */
    @Value("${authorization.access.urls}")
    private String[] urls;
    /**
     * 登录与未登录都放行urls
     */
    @Value("${authorization.pass.urls}")
    private String[] pass_urls;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = requestContext.getRequest();
            String requestURI = request.getRequestURI();
            //价差拦截校验token的urls
            boolean is_contains = checkUrl(requestURI, urls);
            //检查登录与未登录都放行urls
            boolean is_pass = checkUrl(requestURI, pass_urls);
            String accessToken = request.getHeader(CommonConstants.ACCESS_TOKEN);
            //判断访问是否是退出登录
            if (requestURI.contains("/user/logout")) {
                logout(accessToken);
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(200);
                requestContext.addZuulResponseHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
                String responseBody = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":%s}", CodeMsg.SUCCESS.code, CodeMsg.SUCCESS.message, null);
                requestContext.setResponseBody(responseBody);
                return null;
            }
            //不需要token验证直接放行
            if (!is_contains) {
                requestContext.setSendZuulResponse(true);
                requestContext.setResponseStatusCode(200);
                return null;
            }

            if (CommonUtil.isEmpty(accessToken) && !is_pass) {
                throw new TokenCheckException(CodeMsg.E_20006);
            }
            if(CommonUtil.isNotEmpty(accessToken)){
                verifyToken(accessToken, requestContext);
                settingTokenExpireTime(request);
            }
            return null;
        } catch (TokenCheckException e) {
            log.info("error :", e);
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(200);
            requestContext.addZuulResponseHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            String responseBody = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":%s}", e.getCode(), e.getMessage(), null);
            requestContext.setResponseBody(responseBody);
            return null;
        } catch (Exception e) {
            log.info("error :", e);
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(200);
            requestContext.addZuulResponseHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            String responseBody = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":%s}", CodeMsg.E_500.code, CodeMsg.E_500.message, null);
            requestContext.setResponseBody(responseBody);
            return null;
        }
    }

    private boolean checkUrl(String requestURI, String[] urls) {
        boolean is_pass = false;
        //拦截校验token的地址
        if (!CommonUtil.isEmpty(urls)) {
            for (String url : urls) {
                //判断拦截配置是否有*
                if (url.indexOf(SymbolConstants.STAR) != -1) {
                    url = url.substring(0, url.indexOf(SymbolConstants.STAR));
                }
                //如果请求url包含拦截配置url，则进行token验证
                if (requestURI.contains(url)) {
                    is_pass = true;
                    break;
                }
            }
        }
        return is_pass;
    }

    /**
     * @param accessToken 前端header里面的accessToken
     * @return Result
     * @description 验证token是否有效
     * @author WuLong
     * @date 2019/8/2 12:07
     */
    public void verifyToken(String accessToken, RequestContext requestContext) throws TokenCheckException {
        Map<String, String> tokenKeyMap = jwtHelper.verifyToken(accessToken);
        if (CommonUtil.isNotEmpty(tokenKeyMap)) {
            String issue = tokenKeyMap.get("iss");
            if (!issue.equals(jwtProperties.getIssuer())) {
                throw new TokenCheckException(CodeMsg.E_20001);
            }
            //验证token在redis是否过期
            String jwtTokenCacheKey = RedisUtil.getJWTTokenCacheKey(tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_ID),
                    tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_MOBILE));
            Object o = redisUtil.get(jwtTokenCacheKey);
            //验证缓存是否失效
            if (o == null) {
                throw new TokenCheckException(CodeMsg.E_20001);
            }
            Map<String, String> accountMap = jwtHelper.verifyToken(o.toString());
            String is_usable = accountMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_USABLE);
            String tokenKeyIp = tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IP);
            String tokenKeyUserAgent = tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_USER_AGENT);
            String accountTonkenIp = accountMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IP);
            String accountTonkenUserAgent = accountMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_USER_AGENT);
            if (!tokenKeyIp.equals(accountTonkenIp) || !tokenKeyUserAgent.equals(accountTonkenUserAgent)) {
                throw new TokenCheckException(CodeMsg.E_20012);
            }
            //用户属性判断 是否已经作废
            if (is_usable.equals("0")) {
                throw new TokenCheckException(CodeMsg.E_10018);
            }
            //判断用户是否  设置为不可登录
            String is_login = accountMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_LOGIN);
            if (is_login.equals("0")) {
                throw new TokenCheckException(CodeMsg.E_10019);
            }
            String is_online = accountMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_ONLINE);
            if (is_online.equals("0")) {
                logout(accessToken);
                throw new TokenCheckException(CodeMsg.E_10031);
            }
            settingHeader(requestContext, accountMap);
        }
    }

    private void logout(String accessToken) throws TokenCheckException {
        try {
            Map<String, String> tokenKeyMap = jwtHelper.verifyToken(accessToken);
            if (CommonUtil.isNotEmpty(tokenKeyMap)) {
                String issue = tokenKeyMap.get("iss");
                if (!issue.equals(jwtProperties.getIssuer())) {
                    throw new TokenCheckException(CodeMsg.E_20001);
                }
                //验证token在redis是否过期
                String jwtTokenCacheKey = RedisUtil.getJWTTokenCacheKey(tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_ID),
                        tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_MOBILE));
                redisUtil.del(jwtTokenCacheKey);
            }
        } catch (TokenCheckException e) {
            throw new TokenCheckException(CodeMsg.E_10026);
        }
    }

    /**
     * @param request
     * @description 重置token有效时间
     * @author WuLong
     * @date 2019/8/2 13:38
     */
    private void settingTokenExpireTime(HttpServletRequest request) {
        String accessToken = request.getHeader(CommonConstants.ACCESS_TOKEN);
        try {
            if (CommonUtil.isNotEmpty(accessToken)) {
                Map<String, String> tokenKeyMap = jwtHelper.verifyToken(accessToken);
                if (CommonUtil.isNotEmpty(tokenKeyMap)) {
                    String issue = tokenKeyMap.get("iss");
                    //issuer是否匹配
                    if (!issue.equals(jwtProperties.getIssuer())) {
                        log.info("token重置过期时间异常:issuer不匹配:{}", JsonUtil.obj2json(tokenKeyMap));
                        return;
                    }
                    //验证token在redis是否过期
                    String jwtTokenCacheKey = RedisUtil.getJWTTokenCacheKey(tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_ID),
                            tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_MOBILE));
                    Object o = redisUtil.get(jwtTokenCacheKey);
                    //验证缓存是否失效
                    if (o == null) {
                        log.info("token重置过期时间异常:缓存不存在:{}", jwtTokenCacheKey);
                    }
                    Map<String, String> accountMap = jwtHelper.verifyToken(o.toString());
                    int auto = Integer.valueOf(accountMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_AUTO));
                    long expireTime = jwtProperties.getExpireTime();
                    if (auto == 1) {
                        expireTime = jwtProperties.getWeekExpireTime();
                    }
                    //重新设置redis过期时间
                    redisUtil.expire(jwtTokenCacheKey, expireTime);
                    log.info("token重置过期时间成功：{}", JsonUtil.obj2json(tokenKeyMap));
                }
            }
        } catch (Exception e) {
            log.error("token重置过期时间异常：{},exception message :{},e:{}", accessToken, e.getMessage(), e);
        }
    }

    /**
     * @author WuLong
     * @date 2019/7/30 16:23
     * @description set头部信息
     */
    private void settingHeader(RequestContext ctx, Map<String, String> map) {
        try {
            // 添加account进入头信息
            ctx.addZuulRequestHeader(CommonConstants.ZUUL_HEADER_DATA,
                    URLEncoder.encode(JsonUtil.obj2json(map), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}