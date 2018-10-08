package com.nekolr.config;

import com.nekolr.common.Constants;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * 应用配置类
 * <p>
 * 1. 扫描基本包下的除 Controller 和 Configuration 的注解
 * 2. 导入 JavaConfig
 *
 * @author nekolr
 */
@Configuration
@ComponentScan(basePackages = Constants.BASE_PACKAGE_PATH,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, Configuration.class})})
@Import(value = {
        PropertiesConfig.class, MyBatisConfig.class, CacheConfig.class, RedisConfig.class})
public class AppConfig {
}
