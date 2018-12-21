package com.nekolr.upms.server.config;

import com.nekolr.config.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(AppConfig.class)
//@ImportResource("classpath:META-INF/spring/dubbo-consumer.xml")
public class UpmsAppConfig {

}
