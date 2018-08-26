package com.nekolr.config;

import com.nekolr.common.Constants;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
public class MvcConfig implements WebMvcConfigurer {

}
