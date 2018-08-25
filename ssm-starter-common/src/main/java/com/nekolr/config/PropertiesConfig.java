package com.nekolr.config;

import com.nekolr.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 配置处理类
 * <p>
 * 多个系统时，修改 @PropertySources 添加多个 @PropertySource，在拆分系统时，
 * 虽然在全局上配置了多个配置文件，而实际上肯定只能找到其中的一个，其他找不到的则会忽略
 *
 * @author nekolr
 */
@Configuration
@PropertySources(value = {
        @PropertySource(value = Constants.UPMS_CONFIG_FILE_PATH, ignoreResourceNotFound = true)
})
public class PropertiesConfig {

    /**
     * 资源文件解析类
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
