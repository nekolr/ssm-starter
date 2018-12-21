package com.nekolr.upms.provider.config.bean;

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
@PropertiesBean(prefix = "framework.dubbo.provider")
public class DubboProviderBean {
    private String applicationName;
    private String applicationLogger;
    private String registryAddress;
    private String registryClient;
    private Integer protocolPort;
    private String protocol;
    private Integer qosPort;
}
