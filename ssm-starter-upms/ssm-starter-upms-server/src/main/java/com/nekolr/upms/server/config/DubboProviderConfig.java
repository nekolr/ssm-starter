package com.nekolr.upms.server.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.nekolr.config.bean.DubboProviderBean;
import com.nekolr.upms.common.UpmsConstants;
import com.nekolr.util.EnvironmentUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@DubboComponentScan(basePackages = UpmsConstants.DUBBO_PROVIDER_BASE_PACKAGE)
public class DubboProviderConfig {

    /**
     * 应用配置
     *
     * @param environment
     * @return
     * @throws Exception
     */
    @Bean
    public ApplicationConfig applicationConfig(Environment environment) throws Exception {
        DubboProviderBean dubboProviderBean = EnvironmentUtils.toBean(environment, DubboProviderBean.class);

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboProviderBean.getApplicationName());
        applicationConfig.setLogger(dubboProviderBean.getApplicationLogger());

        return applicationConfig;
    }


    /**
     * 注册中心配置
     *
     * @param environment
     * @return
     * @throws Exception
     */
    @Bean
    public RegistryConfig registryConfig(Environment environment) throws Exception {
        DubboProviderBean dubboProviderBean = EnvironmentUtils.toBean(environment, DubboProviderBean.class);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboProviderBean.getRegistryAddress());
        registryConfig.setClient(dubboProviderBean.getRegistryClient());
        registryConfig.setPort(dubboProviderBean.getRegistryPort());
        registryConfig.setProtocol(dubboProviderBean.getRegistryProtocol());

        return registryConfig;
    }

}
