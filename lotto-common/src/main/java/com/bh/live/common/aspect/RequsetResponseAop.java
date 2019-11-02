package com.bh.live.common.aspect;

import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.IpUtil;
import com.bh.live.common.utils.http.RequestResponseUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

/**
 *@author WuLong
 *@date 2019/8/8 9:40
 *@description 打印userAgent、入参、出参
 */
@Aspect
@Order(-1)
@Component
@Slf4j
public class RequsetResponseAop {
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    /**
     * 切点
     */
    @Pointcut("execution(public * com.bh.live.*.controller..*(..))")
    public void httpPointcut() {
    }

    @Before("httpPointcut()")
    public void doBefore(JoinPoint joinPoint){
        //开始计时
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //打印请求的内容
        //获取请求头中的User-Agent
        log.info("接口路径：{}" , request.getRequestURL().toString());
        log.info("IP : {}" , IpUtil.getIpAddress(request));
        log.info("请求类型：{}", request.getMethod());
        log.info("类方法 : " , joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : {} " , joinPoint.getArgs());

    }

    @AfterReturning(returning = "ret" , pointcut = "httpPointcut()")
    public void doAfterReturning(Object ret){
        //处理完请求后，返回内容
        log.info("方法返回值：{}" , ret);
        log.info("方法执行时间：{}毫秒", (System.currentTimeMillis() - startTime.get()));
    }
}
