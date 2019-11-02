package com.bh.live.shiro.filter;

import lombok.NoArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 重写过滤链路径匹配规则
 * 增加REST风格post,get.delete,put..支持
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@NoArgsConstructor
public abstract class AbstractPathMatchingFilter extends PathMatchingFilter {

    private static final String DEFAULT_PATH_SEPARATOR = "/";

    /**
     * description 重写URL匹配  加入httpMethod支持
     *
     * @param path 1
     * @param request 2
     * @return boolean
     */
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = this.getPathWithinApplication(request);
        if (requestURI != null && requestURI.endsWith(DEFAULT_PATH_SEPARATOR)) {
            requestURI = requestURI.substring(0, requestURI.length() - 1);
        }
        String[] strings = path.split("==");
        if (strings[0] != null && strings[0].endsWith(DEFAULT_PATH_SEPARATOR)) {
            strings[0] = strings[0].substring(0 , strings[0].length() - 1);
        }
        if (strings.length <= 1) {
            // 分割出来只有URL
            return this.pathsMatch(strings[0], requestURI);
        } else {
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();
            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestURI);
        }
    }

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * description allowed
     *
     * @param var1 1
     * @param var2 2
     * @param var3 3
     * @return boolean
     * @throws Exception when
     */
    protected abstract boolean isAccessAllowed(ServletRequest var1, ServletResponse var2, Object var3) throws Exception;

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return this.onAccessDenied(request, response);
    }

    /**
     * description denied
     *
     * @param var1 1
     * @param var2 2
     * @return boolean
     * @throws Exception when
     */
    protected abstract boolean onAccessDenied(ServletRequest var1, ServletResponse var2) throws Exception;

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return this.isAccessAllowed(request, response, mappedValue) || this.onAccessDenied(request, response, mappedValue);
    }
}
