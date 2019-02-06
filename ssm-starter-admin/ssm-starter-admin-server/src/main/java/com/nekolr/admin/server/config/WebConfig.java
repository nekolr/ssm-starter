package com.nekolr.admin.server.config;

import com.nekolr.support.XssFilter;

import javax.servlet.FilterRegistration;

/**
 * ssm-admin 系统 Web 配置
 *
 * @author nekolr
 */
public class WebConfig extends AbstractWebInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // 需要在父容器中创建 Dubbo 的服务类，不然无法初始化 ShiroConfig 中的部分业务类
        return new Class[]{DubboConsumerConfig.class, AppConfig.class, ShiroConfig.class};
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
