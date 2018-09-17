package com.nekolr.config.bean;

import com.nekolr.annotation.PropertiesBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Swagger 配置 bean
 */
@Getter
@Setter
@PropertiesBean(prefix = "framework.swagger")
public class SwaggerBean {
    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String host;
    private String name;
    private String url;
    private String email;
}
