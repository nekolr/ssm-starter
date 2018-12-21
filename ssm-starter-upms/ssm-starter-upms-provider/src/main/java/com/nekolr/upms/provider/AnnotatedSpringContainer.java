package com.nekolr.upms.provider;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.container.Container;
import com.nekolr.config.AppConfig;
import org.apache.dubbo.config.spring.initializer.DubboApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Dubbo SPI 扩展容器
 *
 * @author nekolr
 */
public class AnnotatedSpringContainer implements Container {

    private static final Logger logger = LoggerFactory.getLogger(AnnotatedSpringContainer.class);

    static AnnotationConfigApplicationContext context;

    @Override
    public void start() {
        context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.addApplicationListener(new DubboApplicationListener());
        context.registerShutdownHook();
        context.refresh();
        context.start();
    }

    @Override
    public void stop() {
        try {
            if (context != null) {
                context.stop();
                context.close();
                context = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
}
