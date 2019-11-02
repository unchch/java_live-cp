package com.bh.live.filter;

import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.IpUtil;
import com.bh.live.common.utils.http.RequestResponseUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 *@author WuLong
 *@date 2019/8/5 18:01
 *@description 请求日志记录器
 */
@Component
@Slf4j
public class LoggerFilter extends ZuulFilter {

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = ctx.getRequest();
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            String accessToken = request.getHeader(CommonConstants.ACCESS_TOKEN);
            String responseBody = "";
            InputStream data = ctx.getResponseDataStream();
            if (null != data) {
                responseBody = StreamUtils.copyToString(data, Consts.UTF_8);
                byte[] bytes = responseBody.getBytes(Consts.UTF_8);
                ctx.setResponseDataStream(new ServletInputStreamWrapper(bytes));
            }
            log.info("接口路径：{}" , request.getRequestURL().toString());
            log.info("浏览器：{}", userAgent.getBrowser().toString());
            log.info("浏览器版本：{}",userAgent.getBrowserVersion());
            log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
            log.info("IP : {}" , IpUtil.getIpAddress(request));
            log.info("请求类型：{}", request.getMethod());
            log.info("请求参数 : {} ", RequestResponseUtil.getParameters(request));
            log.info("请求头是否token：{}",CommonUtil.isEmpty(accessToken) ? "否" : "是");
            log.info("返回值：{}" , responseBody);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

}
