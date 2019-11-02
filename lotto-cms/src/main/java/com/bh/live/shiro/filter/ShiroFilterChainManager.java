package com.bh.live.shiro.filter;

import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.SpringUtils;
import com.bh.live.shiro.config.RestPathMatchingFilterChainResolver;
import com.bh.live.shiro.dto.RolePermRule;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Filter 管理器
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Component
public class ShiroFilterChainManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroFilterChainManager.class);

    @Autowired
    private com.bh.live.shiro.provider.IShiroFilterRulesProvider IShiroFilterRulesProvider;

    @Autowired
    private PasswordFilter passwordFilter;

    /**
     * description 初始化获取过滤链
     *
     * @return java.util.Map<java.lang.String , javax.servlet.Filter>
     */
    public Map<String, Filter> initGetFilters() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("auth", passwordFilter);
        BonJwtFilter jwtFilter = new BonJwtFilter();
        filters.put("jwt", jwtFilter);
        return filters;
    }

    /**
     * description 初始化获取过滤链规则
     *
     * @return java.util.Map<java.lang.String , java.lang.String>
     */
    public Map<String, String> initGetFilterChain() {
        Map<String, String> filterChain = new LinkedHashMap<>();
        // -------------anon 默认过滤器忽略的URL
        List<String> defalutAnon = Arrays.asList("/css/**","/js/**","/common/**","/druid/**");
        defalutAnon.forEach(ignored -> filterChain.put(ignored,"anon"));
        // -------------system 默认需要认证过滤器的URL 走auth--PasswordFilter
        List<String> defalutAuth = Arrays.asList("/cms/sys/**");
        defalutAuth.forEach(auth -> filterChain.put(auth, "auth"));
        // -------------dynamic 动态URL
        if (IShiroFilterRulesProvider != null) {
            List<RolePermRule> rolePermRules = this.IShiroFilterRulesProvider.loadRolePermRules();
            if (null != rolePermRules) {
                rolePermRules.forEach(rule -> {
                    if (rule == null || CommonUtil.isEmpty(rule.getPath())) {
                        return;
                    }
                    StringBuilder chain = rule.toFilterChain();
                    if (null != chain) {
                        filterChain.putIfAbsent(rule.getPath(), chain.toString());
                    }
                });
            }
        }
        return filterChain;
    }

    /**
     * description 动态重新加载过滤链规则
     */
    public void reloadFilterChain() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = SpringUtils.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter abstractShiroFilter;
        try {
            abstractShiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            RestPathMatchingFilterChainResolver filterChainResolver = (RestPathMatchingFilterChainResolver) abstractShiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            filterChainManager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(this.initGetFilterChain());
            shiroFilterFactoryBean.getFilterChainDefinitionMap().forEach((k, v) -> filterChainManager.createChain(k, v));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
