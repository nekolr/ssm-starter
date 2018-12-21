package com.nekolr.config;

import com.nekolr.config.bean.RedisBean;
import com.nekolr.util.EncryptUtils;
import com.nekolr.util.EnvironmentUtils;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * Redis 配置类
 *
 * @author nekolr
 */
@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory getLettuceConnectionFactory(Environment environment) throws Exception {
        // 获取配置 bean
        RedisBean redisBean = EnvironmentUtils.toBean(environment, RedisBean.class);

        // 单节点配置
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisBean.getHost());
        standaloneConfiguration.setPort(redisBean.getPort());
        standaloneConfiguration.setDatabase(redisBean.getDbIndex());
        standaloneConfiguration.setPassword(RedisPassword.of(EncryptUtils.aesDecrypt(redisBean.getPassword())));

        // 客户端配置
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                // TODO: 暂时不使用 SSL
//                .useSsl()
//                .and()
                .commandTimeout(Duration.ofSeconds(redisBean.getCommandTimeout()))
                .shutdownTimeout(Duration.ofSeconds(redisBean.getShutdownTimeout()))
                .clientResources(clientResources(environment))
                .build();

        // 连接工厂
        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfiguration, clientConfig);
        factory.setValidateConnection(redisBean.getValidateConnection());
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources(Environment environment) throws Exception {
        // 获取配置 bean
        RedisBean redisBean = EnvironmentUtils.toBean(environment, RedisBean.class);

        DefaultClientResources defaultClientResources = DefaultClientResources.builder()
                .ioThreadPoolSize(redisBean.getIoThreadPoolSize())
                .computationThreadPoolSize(redisBean.getComputationThreadPoolSize())
                .build();

        return defaultClientResources;
    }

    /**
     * Redis 字节模板
     *
     * @param environment
     * @param factory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(Environment environment, LettuceConnectionFactory factory) throws Exception {
        // 获取配置 bean
        RedisBean redisBean = EnvironmentUtils.toBean(environment, RedisBean.class);

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setEnableTransactionSupport(redisBean.getEnableTransactionSupport());

        return redisTemplate;
    }

    /**
     * Redis String 模板
     *
     * @param environment
     * @param factory
     * @return
     */
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(Environment environment, RedisConnectionFactory factory) throws Exception {
        // 获取配置 bean
        RedisBean redisBean = EnvironmentUtils.toBean(environment, RedisBean.class);

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setEnableTransactionSupport(redisBean.getEnableTransactionSupport());
        stringRedisTemplate.setConnectionFactory(factory);

        return stringRedisTemplate;
    }

}
