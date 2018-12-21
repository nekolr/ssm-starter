package com.nekolr.upms.server.config.bean;

import com.nekolr.annotation.PropertiesBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Dubbo 配置 bean
 *
 * @author nekolr
 */
@Getter
@Setter
@PropertiesBean(prefix = "framework.dubbo.consumer")
public class DubboConsumerBean {
    private String applicationName;
    private String applicationLogger;
    private String registryAddress;
    private String registryClient;
    private Integer protocolPort;
    private String protocol;
    private Integer consumerTimeout;
    private Integer qosPort;
}
