package com.nekolr.config.bean;

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
    private Integer registryPort;
    private String registryProtocol;
    private Integer consumerTimeout;
}
