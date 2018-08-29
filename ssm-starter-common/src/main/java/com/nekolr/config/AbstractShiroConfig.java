package com.nekolr.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apache Shiro 配置类
 *
 * @author nekolr
 */
@Configuration
public abstract class AbstractShiroConfig {

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
