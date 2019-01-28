package com.nekolr.admin.server.config;

import com.nekolr.admin.server.shiro.config.RestShiroFilterFactoryBean;
import com.nekolr.admin.server.shiro.filter.FilterChainManager;
import com.nekolr.admin.server.shiro.filter.RestWebSubjectFactory;
import com.nekolr.admin.server.shiro.realm.AssignableModularRealmAuthenticator;
import com.nekolr.admin.server.shiro.realm.RealmManager;
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, FilterChainManager filterChainManager) throws Exception {
        // 创建重写的 ShiroFilterFactoryBean
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        // 设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置所有的过滤器，通过 FilterChainManager 管理和初始化
        shiroFilterFactoryBean.setFilters(filterChainManager.initFilters());
        // 设置过滤链规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainManager.initFilterChainDefinitions());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(RealmManager realmManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 身份认证器
        securityManager.setAuthenticator(new AssignableModularRealmAuthenticator());
        // 通过 SecurityManager 获取 SubjectDAO
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        // 通过 SubjectDAO 获取 SessionStorageEvaluator
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        // 新建 RestWebSubjectFactory，传入 SessionStorageEvaluator，创建主体时不创建 session
        RestWebSubjectFactory subjectFactory = new RestWebSubjectFactory(evaluator);
        securityManager.setSubjectFactory(subjectFactory);
        // 设置 Realm 集合，通过 RealmManager 管理和初始化
        securityManager.setRealms(realmManager.initRealms());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     * 管理 Shiro 的 bean 的生命周期
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}