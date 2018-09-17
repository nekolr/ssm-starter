package com.nekolr.config;

import com.nekolr.common.Constants;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类
 * <p>
 * 一定要 useDefaultFilters = false，不然还会扫描 @Component 的子注解 @Service、@Repository
 *
 * @author nekolr
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = Constants.BASE_PACKAGE_PATH, useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})
})
@Import(SwaggerConfig.class)
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 自定义资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问 swagger-ui.html，实际访问的是 classpath:/META-INF/resources/swagger-ui.html
        // 访问 /webjars/**，实际访问的是 classpath:/META-INF/resources/webjars/**
        registry.addResourceHandler("swagger-ui.html", "/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/");
    }
}
