package com.nekolr.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring ApplicationContext 持有者
 *
 * 在 Spring 上下文加载完毕后会通过 setApplicationContext 方法注入 applicationContext 属性
 *
 * @author nekolr
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> type) {
        assertApplicationContext();
        return applicationContext.getBean(type);
    }

    public static <T> T getBean(String beanName, Class<T> type) {
        assertApplicationContext();
        return applicationContext.getBean(beanName, type);
    }

    private static void assertApplicationContext() {
        if (applicationContext == null) {
            throw new RuntimeException("Spring ApplicationContext 为空，请检查它是否被注入");
        }
    }
}
