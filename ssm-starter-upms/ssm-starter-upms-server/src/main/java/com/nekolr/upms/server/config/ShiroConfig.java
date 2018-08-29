package com.nekolr.upms.server.config;

import com.nekolr.shiro.config.RestShiroFilterFactoryBean;
import com.nekolr.upms.server.shiro.filter.FilterChainManager;
import com.nekolr.upms.server.shiro.realm.RealmManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apache Shiro 配置类
 *
 * @author nekolr
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, FilterChainManager filterChainManager) {
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filterChainManager.initFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainManager.initFilterChain());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(RealmManager realmManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(null);
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        securityManager.setSubjectFactory(null);
        securityManager.setRealms(realmManager.initRealms());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     * 管理 shiro 的 bean 的生命周期
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
