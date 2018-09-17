package com.nekolr.config;

import com.nekolr.config.bean.SwaggerBean;
import com.nekolr.util.EnvironmentUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 配置类
 *
 * @author nekolr
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(Environment environment) throws Exception {
        // 获取配置 bean
        SwaggerBean swaggerBean = EnvironmentUtils.toBean(environment, SwaggerBean.class);

        return new Docket(DocumentationType.SWAGGER_2)
                // 域名
                .host(swaggerBean.getHost())
                // api 接口信息
                .apiInfo(apiInfo(swaggerBean))
                // 通过 select 返回一个 ApiSelectorBuilder 对接口进行细粒度控制
                .select()
                // 生成该包下的所有控制器接口
                .apis(RequestHandlerSelectors.basePackage(swaggerBean.getBasePackage()))
                // 任意路径（线上环境要换成 PathSelectors.none()）
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo(SwaggerBean swaggerBean) {
        return new ApiInfoBuilder()
                .title(swaggerBean.getTitle())
                .description(swaggerBean.getDescription())
                .contact(new Contact(swaggerBean.getName(), swaggerBean.getUrl(), swaggerBean.getEmail()))
                .version(swaggerBean.getVersion())
                .build();
    }
}
