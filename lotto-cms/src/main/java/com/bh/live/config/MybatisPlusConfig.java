package com.bh.live.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lgs
 * @title: MybatisPlusConfig
 * @projectName java_live-cp
 * @description: mybatis-plus 分页配置
 * @date 2019/7/25  14:21
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.bh.live.dao*")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /** paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);*/
        return paginationInterceptor;
    }

}
