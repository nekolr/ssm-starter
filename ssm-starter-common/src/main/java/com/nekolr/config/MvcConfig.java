package com.nekolr.config;

import com.nekolr.common.Constants;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类
 * <p>
 * 1、一定要 useDefaultFilters = false，不然还会扫描 @Component 的子注解 @Service、@Repository。
 * <p>
 * 2、一定要将 @EnableAspectJAutoProxy 放在 Spring MVC 配置处。因为父子容器的关系，子容器可以访问父容器，而父容器不可以访问子容器。
 * 加上此处只将 @Controller 扫描到了子容器中，所以需要在子容器中配置开启 AspectJ 自动代理才能找到 Controller 切入点。
 *
 * @author nekolr
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = Constants.BASE_PACKAGE_PATH, useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
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

    /**
     * 自定义参数校验，使用 hibernate-validator
     *
     * @return
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        // 使用 HibernateValidator
        localValidatorFactoryBean.setProviderClass(org.hibernate.validator.HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(hibernateValidationMessages());
        return localValidatorFactoryBean;
    }

    /**
     * 国际化
     *
     * @return
     */
    @Bean
    public ResourceBundleMessageSource hibernateValidationMessages() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        // classpath:/i18n 目录下的国际化资源文件
        resourceBundleMessageSource.setBasenames("i18n/message", "i18n/validation");
        return resourceBundleMessageSource;
    }
}
