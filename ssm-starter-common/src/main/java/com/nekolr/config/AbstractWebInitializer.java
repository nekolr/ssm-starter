package com.nekolr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Web 容器初始化类
 * <p>
 * 在 Servlet 3.0 及以后的版本，Servlet 容器会在 classpath 下搜索实现了 javax.servlet.ServletContainerInitializer 接口的任何类，
 * 找到之后用它来初始化 Servlet 容器，Spring 实现了以上接口，实现类叫做 SpringServletContainerInitializer，
 * 它会依次搜寻实现了 WebApplicationInitializer 的任何类，并委派这个类实现配置。
 * Spring 3.2 开始引入一个简易的 WebApplicationInitializer 实现类 AbstractAnnotationConfigDispatcherServletInitializer
 *
 * @author nekolr
 * @detail <https://blog.nekolr.com/2018/04/27/Servlet%203.1%20%E8%A7%84%E8%8C%83%E5%AD%A6%E4%B9%A0/>
 */
public abstract class AbstractWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
