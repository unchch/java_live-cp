package com.bh.live.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.IpUtil;
import com.bh.live.common.utils.http.RequestResponseUtil;
import com.bh.live.shiro.dto.ValidateMessage;
import com.bh.live.shiro.token.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 支持restful url 的过滤链
 * JWT json web token 过滤器，无状态验证
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class BonJwtFilter extends AbstractPathMatchingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BonJwtFilter.class);

    private static final String STR_EXPIRED = "expiredJwt";

    private static final String STR_OLD = "oldJwt";

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = getSubject();
        if (!isJwtSubmission(servletRequest)) {
            // 请求未携带jwt 判断为无效请求
            Result result = new Result(ValidateMessage.ERROR_REQUEST.getCode(), ValidateMessage.ERROR_REQUEST.getMessage());
            RequestResponseUtil.responseWrite(JSON.toJSONString(result), servletResponse);
            return false;
        }

        boolean isJwtPost = (null != subject && !subject.isAuthenticated());
        // 判断是否为JWT认证请求
        if (isJwtPost) {
            LOGGER.debug("进入JWT验证");
            AuthenticationToken token = createJwtToken(servletRequest);
            try {
                subject.login(token);
                return this.checkRoles(subject, mappedValue);
            } catch (AuthenticationException e) {
                LOGGER.error(String.format("JWT验证失败：%s", e.getMessage()));
                // 如果是JWT过期
                if (STR_EXPIRED.equals(e.getMessage())) {
                    Result result = new Result(ValidateMessage.EXPIRED_JWT.getCode(), ValidateMessage.EXPIRED_JWT.getMessage());
                    RequestResponseUtil.responseWrite(JSON.toJSONString(result), servletResponse);
                    return false;
                }
                //如果是账号被挤掉了
                if (STR_OLD.equals(e.getMessage())) {
                    Result result = new Result(ValidateMessage.OLD_JWT.getCode(), ValidateMessage.OLD_JWT.getMessage());
                    RequestResponseUtil.responseWrite(JSON.toJSONString(result), servletResponse);
                    return false;
                }

                // 其他的判断为JWT错误无效
                Result result = new Result(ValidateMessage.ERROR_JWT.getCode(), ValidateMessage.ERROR_JWT.getMessage());
                RequestResponseUtil.responseWrite(JSON.toJSONString(result), servletResponse);
                return false;

            } catch (Exception e) {
                // 其他错误
                LOGGER.error(IpUtil.getIpAddress(WebUtils.toHttp(servletRequest)) + "--JWT认证失败" + e.getMessage(), e);
                // 告知客户端JWT错误1007,需重新登录申请jwt
                Result result = new Result(ValidateMessage.ERROR_JWT.getCode(), ValidateMessage.ERROR_JWT.getMessage());
                RequestResponseUtil.responseWrite(JSON.toJSONString(result), servletResponse);
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
        Subject subject = getSubject();
        // 未认证的情况上面已经处理  这里处理未授权
        if (subject != null && subject.isAuthenticated()) {
            //  已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            Result result = new Result(ValidateMessage.NO_PERMISSION.getCode(), ValidateMessage.NO_PERMISSION.getMessage());
            RequestResponseUtil.responseWrite(JSON.toJSONString(result), servletResponse);
        }
        // 过滤链终止
        return false;
    }

    /**
     * 检验当前是否是jwt验证请求
     * 只要请求头携带 authorization 和 uid 的都会认为是 需要jwt验证请求
     *
     * @param request
     * @return
     */
    private boolean isJwtSubmission(ServletRequest request) {
        String jwt = RequestResponseUtil.getHeader(request, "authorization");
        String uid = RequestResponseUtil.getHeader(request, "uid");
        return (request instanceof HttpServletRequest)
                && !StringUtils.isEmpty(jwt)
                && !"null".equalsIgnoreCase(jwt)
                && !StringUtils.isEmpty(uid);
    }

    /**
     * 生成jwt token
     *
     * @param request
     * @return
     */
    private AuthenticationToken createJwtToken(ServletRequest request) {
        Map<String, String> maps = RequestResponseUtil.getRequestHeaders(request);
        String uid = maps.get("uid");
        String ipHost = request.getRemoteAddr();
        String jwt = maps.get("authorization");
        String deviceInfo = maps.get("deviceinfo");
        return new JwtToken(uid, ipHost, jwt, deviceInfo);
    }

    /**
     * @param subject     1
     * @param mappedValue 2
     * @return boolean
     * @description 验证当前用户是否属于mappedValue任意一个角色
     */
    private boolean checkRoles(Subject subject, Object mappedValue) {
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray == null || rolesArray.length == 0 || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }

}
