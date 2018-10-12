package com.nekolr.upms.server.config;

import com.nekolr.config.AbstractWebInitializer;
import com.nekolr.config.AppConfig;
import com.nekolr.support.XssFilter;

import javax.servlet.FilterRegistration;

/**
 * UPMS 系统 Web 配置
 *
 * @author nekolr
 */
public class WebConfig extends AbstractWebInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class, ShiroConfig.class};
    }

    @Override
    protected boolean openDruidStatView() {
        return true;
    }

    @Override
    protected void registerCustomFilters() {
        this.registerXssFilter();
    }

    @Override
    protected void registerCustomServlets() {

    }

    /**
     * 注册 XssFilter
     */
    private void registerXssFilter() {
        XssFilter xssFilter = new XssFilter();
        FilterRegistration.Dynamic registration = this.servletContext.addFilter("xssFilter", xssFilter);
        // 配置过滤的 URL，放到最前面
        registration.addMappingForUrlPatterns(getDispatcherTypes(), Boolean.FALSE, "/*");
    }
}
