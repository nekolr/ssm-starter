package com.nekolr.config.bean;

import com.nekolr.annotation.PropertiesBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Redis 配置 bean
 *
 * @author nekolr
 */
@Getter
@Setter
@PropertiesBean(prefix = "framework.redis")
public class RedisBean {
    private String host;
    private Integer port;
    private String password;
    private Integer dbIndex;
    private Boolean validateConnection;
    private Boolean enableTransactionSupport;
}
