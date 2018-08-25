package com.nekolr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * MyBatis 配置类
 *
 * @author nekolr
 */
@Configuration
@Import(DataSourceConfig.class)
public class MyBatisConfig {

}
