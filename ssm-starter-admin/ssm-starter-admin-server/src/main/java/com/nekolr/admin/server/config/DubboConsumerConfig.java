package com.nekolr.admin.server.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.nekolr.admin.server.config.bean.DubboConsumerBean;
import com.nekolr.util.EnvironmentUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Dubbo 服务消费者配置类
 *
 * @author nekolr
 */
@Configuration
@DubboComponentScan(basePackages = "com.nekolr.admin")
public class DubboConsumerConfig {

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
        DubboConsumerBean dubboConsumerBean = EnvironmentUtils.toBean(environment, DubboConsumerBean.class);

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboConsumerBean.getApplicationName());
        applicationConfig.setLogger(dubboConsumerBean.getApplicationLogger());
        applicationConfig.setQosPort(dubboConsumerBean.getQosPort());
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
        DubboConsumerBean dubboConsumerBean = EnvironmentUtils.toBean(environment, DubboConsumerBean.class);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboConsumerBean.getRegistryAddress());
        registryConfig.setClient(dubboConsumerBean.getRegistryClient());
        registryConfig.setProtocol(dubboConsumerBean.getProtocol());
        registryConfig.setClient(dubboConsumerBean.getRegistryClient());
        // 根节点
        registryConfig.setGroup(dubboConsumerBean.getRegistryGroup());
        registryConfig.setVersion(dubboConsumerBean.getRegistryVersion());
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
        /// 获取配置 bean
        DubboConsumerBean dubboConsumerBean = EnvironmentUtils.toBean(environment, DubboConsumerBean.class);

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(dubboConsumerBean.getProtocolPort());
        return protocolConfig;
    }

    /**
     * 消费者配置
     *
     * @param environment
     * @return
     * @throws Exception
     */
    @Bean
    public ConsumerConfig consumerConfig(Environment environment) throws Exception {
        // 获取配置 bean
        DubboConsumerBean dubboConsumerBean = EnvironmentUtils.toBean(environment, DubboConsumerBean.class);

        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(dubboConsumerBean.getConsumerTimeout());
        return consumerConfig;
    }
}
