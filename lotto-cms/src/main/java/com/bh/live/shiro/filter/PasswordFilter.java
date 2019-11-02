package com.bh.live.shiro.filter;


import com.alibaba.fastjson.JSON;
import com.bh.live.common.constant.ResourceKeyConstants;
import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.utils.http.RequestResponseUtil;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.token.ShiroSubUtil;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.IpUtil;
import com.bh.live.common.utils.MessageUtils;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.shiro.dto.ValidateMessage;
import com.bh.live.shiro.token.PasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 基于 用户名密码 的认证过滤器
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Component
public class PasswordFilter extends AccessControlFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordFilter.class);

    @Autowired
    private RedisUtil redisManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        if (null != subject && subject.isAuthenticated()) {
            return CommonUtil.isNotEmpty(ShiroSubUtil.getAccountInfo().getUid());
        }
        // 如果其已经登录，再此发送登录请求
        //  拒绝，统一交给 onAccessDenied 处理
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        // 判断是否是登录请求
        if (isPasswordLoginPost(request)) {
            AuthenticationToken authenticationToken;
            try {
                authenticationToken = createPasswordToken(request);
            } catch (NullPointerException e) {
                LOGGER.error("AES加密 秘钥解析空指针异常" + "::" + e.getMessage(), e);
                Result message = new Result(ValidateMessage.WRONG_TOKEN_KEY.getCode(), ValidateMessage.WRONG_TOKEN_KEY.getMessage());
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            } catch (Exception e) {
                // response 告知无效请求
                Result message = new Result(ValidateMessage.ERROR_REQUEST.getCode(), ValidateMessage.ERROR_REQUEST.getMessage());
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            }


            Subject subject = getSubject(request, response);
            try {
                subject.login(authenticationToken);
                //登录认证成功,进入请求派发json web token url资源内
                return true;
            } catch (DisabledAccountException e) {
                LOGGER.warn(authenticationToken.getPrincipal() + "::" + e.getMessage());
                // 返回response告诉客户端认证失败
                Result message = new Result(ValidateMessage.DISABLED_ACCOUNT.getCode(), ValidateMessage.DISABLED_ACCOUNT.getMessage());
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            } catch (AuthenticationException e) {
                LOGGER.warn(authenticationToken.getPrincipal() + "::" + e.getMessage());
                // 返回response告诉客户端认证失败
                Result message = new Result(ValidateMessage.WRONG_PASS.getCode(), ValidateMessage.WRONG_PASS.getMessage());
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            } catch (Exception e) {
                LOGGER.error(authenticationToken.getPrincipal() + "::认证异常::" + e.getMessage(), e);
                // 返回response告诉客户端认证失败
                Result message = new Result(ValidateMessage.WRONG_PASS.getCode(), MessageUtils.message(ResourceKeyConstants.COMMON_FAIL, "登录"));
                RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
                return false;
            }
        }
        // 判断是否为注册请求,若是通过过滤链进入controller注册
        if (isAccountRegisterPost(request)) {
            return true;
        }
        // 之后添加对账户的找回等
        // response 告知无效请求
        Result message = new Result(ValidateMessage.ERROR_REQUEST.getCode(), ValidateMessage.ERROR_REQUEST.getMessage());
        RequestResponseUtil.responseWrite(JSON.toJSONString(message), response);
        return false;
    }

    /**
     * 判断是否是使用密码登陆的请求
     *
     * @param request
     * @return
     */
    private boolean isPasswordLoginPost(ServletRequest request) {
        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
        String uid = map.get("uid");
        String password = map.get("password");
        String timestamp = map.get("timestamp");
        String methodName = map.get("methodName");
        return (request instanceof HttpServletRequest)
                && "POST".equals(((HttpServletRequest) request).getMethod().toUpperCase())
                && null != password
                && null != timestamp
                && null != uid
                && "login".equals(methodName);
    }

    /**
     * 判断是否是账户登录的请求
     *
     * @param request
     * @return
     */
    private boolean isAccountRegisterPost(ServletRequest request) {
        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
        String uid = map.get("uid");
        String username = map.get("username");
        String methodName = map.get("methodName");
        String password = map.get("password");
        return (request instanceof HttpServletRequest)
                && "POST".equals(((HttpServletRequest) request).getMethod().toUpperCase())
                && null != username
                && null != password
                && null != uid
                && "register".equals(methodName);
    }

    /**
     * 创建 PasswordToken
     *
     * @param request
     * @return
     */
    private AuthenticationToken createPasswordToken(ServletRequest request) {
        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
        String uid = map.get("uid");
        String timestamp = map.get("timestamp");
        String qrcode = map.get("qrcode");
        String password = map.get("password");
        String host = IpUtil.getIpAddress(WebUtils.toHttp(request));
        String userKey = map.get("userKey");
        String tokenKey = redisManager.getByFastJson(String.format(UserRedisKey.SYS_USER_TOKEN_KEY, host.toUpperCase() + userKey), String.class);
        return new PasswordToken(uid, password, qrcode, timestamp, host, tokenKey);
    }

}
