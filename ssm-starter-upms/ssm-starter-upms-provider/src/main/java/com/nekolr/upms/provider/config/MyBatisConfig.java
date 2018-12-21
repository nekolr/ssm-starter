package com.nekolr.upms.provider.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.nekolr.upms.provider.config.bean.MyBatisBean;
import com.nekolr.util.EnvironmentUtils;
import com.nekolr.util.PackageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
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
    public SqlSessionFactory getSqlSessionFactoryBean(DataSource dataSource, Environment environment) throws Exception {
        // 获取配置 bean
        MyBatisBean myBatisBean = EnvironmentUtils.toBean(environment, MyBatisBean.class);

        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();

        // MyBatis Configuration 设置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setCacheEnabled(myBatisBean.getCacheEnabled());
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
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
        // 结果集为 java.util.Map 时将查询结果的下划线自动转驼峰
        configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
        // 设置数据源
        factoryBean.setDataSource(dataSource);

        // 设置配置项
        factoryBean.setConfiguration(configuration);

        // MyBatis Plus 全局配置
        factoryBean.setGlobalConfig(globalConfig());

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
        factoryBean.setPlugins(new Interceptor[]{
                // 分页插件
                getPageInterceptor(),
                // 性能拦截器，兼 SQL 打印，生产环境不建议配置
                new PerformanceInterceptor(),
                // 乐观锁插件
                new OptimisticLockerInterceptor()
        });

        // 设置映射 xml 路径
        factoryBean.setMapperLocations(getMapperLocations(myBatisBean));

        return factoryBean.getObject();
    }

    /**
     * 扫描配置，如果不使用 @MapperScan 注解，则需要将扫描方法设置为 static
     *
     * @param environment
     * @return
     * @throws Exception
     */
    @Bean
    public static MapperScannerConfigurer getMapperScannerConfigurer(Environment environment) throws Exception {
        MyBatisBean myBatisBean = EnvironmentUtils.toBean(environment, MyBatisBean.class);
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(myBatisBean.getBasePackage());
        return mapperScannerConfigurer;
    }

    /**
     * MyBatis Plus 全局配置
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig config = GlobalConfigUtils.defaults();
        // 逻辑删除注入
        config.setSqlInjector(new LogicSqlInjector());
        return config;
    }

    /**
     * mybatis plus 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor getPageInterceptor() {
        PaginationInterceptor pageInterceptor = new PaginationInterceptor();
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
