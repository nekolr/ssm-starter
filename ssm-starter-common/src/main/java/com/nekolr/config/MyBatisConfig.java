package com.nekolr.config;

import com.nekolr.config.bean.MyBatisBean;
import com.nekolr.config.bean.PageHelperBean;
import com.nekolr.util.EnvironmentUtils;
import com.nekolr.util.PackageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * MyBatis 配置类
 *
 * @author nekolr
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@Import(DataSourceConfig.class)
public class MyBatisConfig {

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource, Environment environment) throws Exception {
        // 获取配置 bean
        MyBatisBean myBatisBean = EnvironmentUtils.toBean(environment, MyBatisBean.class);

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // MyBatis Configuration 设置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(myBatisBean.getCacheEnabled());
        configuration.setLazyLoadingEnabled(myBatisBean.getLazyLoadingEnabled());
        configuration.setMultipleResultSetsEnabled(myBatisBean.getMultipleResultSetsEnabled());
        configuration.setUseColumnLabel(myBatisBean.getUseColumnLabel());
        configuration.setUseGeneratedKeys(myBatisBean.getUseGeneratedKeys());
        configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(myBatisBean.getAutoMappingBehavior()));
        configuration.setDefaultExecutorType(ExecutorType.valueOf(myBatisBean.getDefaultExecutorType()));
        configuration.setSafeRowBoundsEnabled(myBatisBean.getSafeRowBoundsEnabled());
        configuration.setMapUnderscoreToCamelCase(myBatisBean.getMapUnderscoreToCamelCase());
        configuration.setLocalCacheScope(LocalCacheScope.valueOf(myBatisBean.getLocalCacheScope()));
        configuration.setJdbcTypeForNull(JdbcType.valueOf(myBatisBean.getJdbcTypeForNull()));
        configuration.setLazyLoadTriggerMethods(new HashSet<>(Arrays.asList(myBatisBean.getLazyLoadTriggerMethods().split(","))));
        configuration.setAggressiveLazyLoading(myBatisBean.getAggressiveLazyLoading());

        // 设置配置项
        factoryBean.setConfiguration(configuration);

        // 设置实体别名
        if (StringUtils.isNotEmpty(myBatisBean.getTypeAliasesPackage())) {
            List<String> typeAliasesPackageList = PackageUtils
                    .scannerPackageName(org.springframework.util.StringUtils
                            .tokenizeToStringArray(myBatisBean.getTypeAliasesPackage(), ","));
            if (typeAliasesPackageList != null && typeAliasesPackageList.size() != 0) {
                String typeAliasesPackage = StringUtils.join(typeAliasesPackageList, ",");

                // 别名包目录设置
                factoryBean.setTypeAliasesPackage(typeAliasesPackage);
            }
        }

        // 设置插件
        factoryBean.setPlugins(new Interceptor[]{getPageInterceptor(environment)});

        // 设置映射 xml 路径
        factoryBean.setMapperLocations(getMapperLocations(myBatisBean));

        return factoryBean;
    }

    /**
     * 扫描配置
     *
     * @param environment
     * @return
     * @throws Exception
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(Environment environment) throws Exception {
        MyBatisBean myBatisBean = EnvironmentUtils.toBean(environment, MyBatisBean.class);
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(myBatisBean.getBasePackage());
        return mapperScannerConfigurer;
    }

    /**
     * mybatis 分页插件
     *
     * @return
     */
    @Bean(name = "pageHelper")
    public Interceptor getPageInterceptor(Environment environment) throws Exception {
        PageHelperBean pageHelperBean = EnvironmentUtils.toBean(environment, PageHelperBean.class);
        Interceptor pageInterceptor = new com.github.pagehelper.PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", pageHelperBean.getHelperDialect());
        properties.setProperty("reasonable", String.valueOf(pageHelperBean.getReasonable()));
        properties.setProperty("supportMethodsArguments", String.valueOf(pageHelperBean.getSupportMethodsArguments()));
        properties.setProperty("params", pageHelperBean.getParams());
        properties.setProperty("autoRuntimeDialect", String.valueOf(pageHelperBean.getAutoRuntimeDialect()));
        properties.setProperty("closeConn", String.valueOf(pageHelperBean.getCloseConn()));
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }


    /**
     * 事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }


    /**
     * 获取 mapper xml 映射路径
     *
     * @return
     * @throws IOException
     */
    public org.springframework.core.io.Resource[] getMapperLocations(MyBatisBean myBatisBean) throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        org.springframework.core.io.Resource[] resource = patternResolver.getResources(myBatisBean.getMapperLocations());
        return resource;
    }
}
