package com.nekolr.config.bean;

import com.nekolr.annotation.PropertiesBean;
import lombok.Getter;
import lombok.Setter;

/**
 * MyBatis 分页插件 bean
 *
 * @author nekolr
 */
@Getter
@Setter
@PropertiesBean(prefix = "framework.pagehelper")
public class PageHelperBean {
    private String helperDialect;
    private Boolean reasonable;
    private Boolean supportMethodsArguments;
    private String params;
    private Boolean autoRuntimeDialect;
    private Boolean closeConn;
}
