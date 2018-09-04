package com.nekolr.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

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

    protected ServletContext servletContext;

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

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        this.servletContext = servletContext;
        initCustomFilters();
        initCustomServlets();
    }

    /**
     * 是否开启 Druid 连接池监控
     *
     * @return
     */
    protected abstract boolean openDruidStatView();

    /**
     * 拦截类型
     *
     * @return
     */
    private EnumSet<DispatcherType> getDispatcherTypes() {
        return isAsyncSupported() ?
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC) :
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);
    }

    /**
     * 初始化自定义 Servlet
     */
    private void initCustomServlets() {
        this.initDruidStatViewServlet();
        this.registerCustomServlets();
    }

    /**
     * 初始化自定义过滤器
     */
    private void initCustomFilters() {
        this.initCharacterEncodingFilter();
        this.initShiroFilter();
        this.registerCustomFilters();
    }

    /**
     * 初始化 Shiro 过滤器
     */
    private void initShiroFilter() {
        // 将过滤器纳入 Spring 容器管理
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetFilterLifecycle(Boolean.TRUE);
        delegatingFilterProxy.setTargetBeanName("shiroFilter");
        FilterRegistration.Dynamic registration = this.servletContext.addFilter("shiroFilter", delegatingFilterProxy);
        registration.addMappingForUrlPatterns(getDispatcherTypes(), Boolean.FALSE, "/*");
    }

    /**
     * 初始化字符编码过滤器
     */
    private void initCharacterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(Boolean.TRUE);
        FilterRegistration.Dynamic registration = this.servletContext.addFilter("characterEncodingFilter", encodingFilter);
        // 配置过滤的 URL，放到最前面
        registration.addMappingForUrlPatterns(getDispatcherTypes(), Boolean.FALSE, "/*");
    }


    /**
     * 初始化 Druid 连接池监控 Servlet
     */
    private void initDruidStatViewServlet() {
        if (openDruidStatView()) {
            StatViewServlet statViewServlet = new StatViewServlet();
            ServletRegistration.Dynamic registration = this.servletContext.addServlet("druidStatViewServlet", statViewServlet);
            registration.setLoadOnStartup(0);
            registration.addMapping(new String[]{"/druid/*"});
            registration.setAsyncSupported(isAsyncSupported());
            registration.setInitParameter("loginUsername", "druid");
            registration.setInitParameter("loginPassword", "druid");
        }
    }

    /**
     * 注册自定义的 Filter
     */
    protected abstract void registerCustomFilters();

    /**
     * 注册自定义的 Servlet
     */
    protected abstract void registerCustomServlets();
}
