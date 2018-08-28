package com.nekolr.config.bean;

import com.nekolr.annotation.PropertiesBean;
import lombok.Getter;
import lombok.Setter;

/**
 * MyBatis 配置 bean
 *
 * @author nekolr
 */
@Getter
@Setter
@PropertiesBean(prefix = "framework.mybatis")
public class MyBatisBean {
    private Boolean cacheEnabled;
    private Boolean lazyLoadingEnabled;
    private Boolean multipleResultSetsEnabled;
    private Boolean useColumnLabel;
    private Boolean useGeneratedKeys;
    private String autoMappingBehavior;
    private String defaultExecutorType;
    private Boolean safeRowBoundsEnabled;
    private Boolean mapUnderscoreToCamelCase;
    private String localCacheScope;
    private String jdbcTypeForNull;
    private String lazyLoadTriggerMethods;
    private Boolean aggressiveLazyLoading;
    private String typeAliasesPackage;
    private String mapperLocations;
    private String basePackage;
}
