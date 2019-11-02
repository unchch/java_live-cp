package com.bh.live.shiro.config;

import com.bh.live.shiro.filter.ShiroFilterChainManager;
import com.bh.live.shiro.filter.StatelessWebSubjectFactory;
import com.bh.live.shiro.realm.AonModularRealmAuthenticator;
import com.bh.live.shiro.realm.RealmManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroFilterChainManager filterChainManager) {
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filterChainManager.initGetFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainManager.initGetFilterChain());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(RealmManager realmManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(new AonModularRealmAuthenticator());
        securityManager.setRealms(realmManager.initGetRealm());
        // 无状态subjectFactory设置
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator)((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(Boolean.FALSE);
        StatelessWebSubjectFactory subjectFactory = new StatelessWebSubjectFactory();
        securityManager.setSubjectFactory(subjectFactory);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }
}