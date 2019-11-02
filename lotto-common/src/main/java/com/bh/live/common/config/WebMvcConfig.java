package com.bh.live.common.config;

import com.bh.live.common.interceptor.CheckParamsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    CheckParamsInterceptor checkParamsInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //如果除了接口的请求还有其他请求的话可以在所有的接口前面加个前缀区分开
        registry.addInterceptor(checkParamsInterceptor).addPathPatterns("/**");
    }

    /**
     * 使用cors处理跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
