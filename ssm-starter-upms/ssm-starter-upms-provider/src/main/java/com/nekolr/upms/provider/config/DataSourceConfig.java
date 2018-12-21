package com.nekolr.upms.provider.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.nekolr.upms.provider.config.bean.DataSourceBean;
import com.nekolr.util.EncryptUtils;
import com.nekolr.util.EnvironmentUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * 数据源配置类
 *
 * @author nekolr
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource getDataSource(Environment environment) throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        // 获取配置 bean
        DataSourceBean dataSourceBean = EnvironmentUtils.toBean(environment, DataSourceBean.class);

        // 设置属性
        dataSource.setDriverClassName(dataSourceBean.getDriverClass());
        dataSource.setUrl(dataSourceBean.getUrl());
        dataSource.setUsername(dataSourceBean.getUsername());
        dataSource.setPassword(EncryptUtils.aesDecrypt(dataSourceBean.getPassword()));
        dataSource.setInitialSize(dataSourceBean.getInitialSize());
        dataSource.setMinIdle(dataSourceBean.getMinIdle());
        dataSource.setMaxActive(dataSourceBean.getMaxActive());
        dataSource.setTimeBetweenConnectErrorMillis(dataSourceBean.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dataSourceBean.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(dataSourceBean.getValidationQuery());
        dataSource.setTestWhileIdle(dataSourceBean.getTestWhileIdle());
        dataSource.setTestOnBorrow(dataSourceBean.getTestOnBorrow());
        dataSource.setTestOnReturn(dataSourceBean.getTestOnReturn());
        dataSource.setPoolPreparedStatements(dataSourceBean.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceBean.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setFilters(dataSourceBean.getFilters());

        return dataSource;
    }
}
