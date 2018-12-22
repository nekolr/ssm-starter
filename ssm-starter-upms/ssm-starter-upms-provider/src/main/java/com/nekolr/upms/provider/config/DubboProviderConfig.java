package com.nekolr.upms.provider.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.nekolr.upms.provider.config.bean.DubboProviderBean;
import com.nekolr.util.EnvironmentUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Dubbo 服务提供者配置类
 *
 * @author nekolr
 */
@Configuration
@DubboComponentScan(basePackages = "com.nekolr.upms.provider")
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
        // 获取配置 bean
        DubboProviderBean dubboProviderBean = EnvironmentUtils.toBean(environment, DubboProviderBean.class);

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboProviderBean.getApplicationName());
        applicationConfig.setLogger(dubboProviderBean.getApplicationLogger());
        applicationConfig.setQosPort(dubboProviderBean.getQosPort());
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
        // 获取配置 bean
        DubboProviderBean dubboProviderBean = EnvironmentUtils.toBean(environment, DubboProviderBean.class);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboProviderBean.getRegistryAddress());
        registryConfig.setClient(dubboProviderBean.getRegistryClient());
        registryConfig.setProtocol(dubboProviderBean.getProtocol());
        registryConfig.setClient(dubboProviderBean.getRegistryClient());
        // 根节点
        registryConfig.setGroup(dubboProviderBean.getRegistryGroup());
        registryConfig.setVersion(dubboProviderBean.getRegistryVersion());
        return registryConfig;
    }

    /**
     * 协议配置
     *
     * @param environment
     * @return
     * @throws Exception
     */
    @Bean
    public ProtocolConfig protocolConfig(Environment environment) throws Exception {
        // 获取配置 bean
        DubboProviderBean dubboProviderBean = EnvironmentUtils.toBean(environment, DubboProviderBean.class);

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(dubboProviderBean.getProtocolPort());
        return protocolConfig;
    }
}
