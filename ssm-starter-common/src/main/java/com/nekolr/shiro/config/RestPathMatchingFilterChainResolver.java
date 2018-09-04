package com.nekolr.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * REST 风格过滤器链处理器
 *
 * @author nekolr
 */
@Slf4j
public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }
        String requestURI = getPathWithinApplication(request);
        boolean isMatch = false;
        for (String pathPattern : filterChainManager.getChainNames()) {
            // 比如一个资源，url = "api/v1/resource", httpMethod="GET"，那么配置为：api/v1/resource==GET
            String[] pathPatterns = pathPattern.split("==");
            if (pathPatterns.length == 2) {
                // 比较 HTTP METHOD 是否一致，不一致就不匹配
                if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(pathPatterns[1].toUpperCase())) {
                    isMatch = true;
                } else {
                    isMatch = false;
                }
                // 重新赋值
                pathPattern = pathPatterns[0];
            }
            if (pathMatches(pathPattern, requestURI) && isMatch) {
                if (log.isTraceEnabled()) {
                    log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  " +
                            "Utilizing corresponding filter chain...");
                }
                return filterChainManager.proxy(originalChain, pathPattern);
            }
        }
        return null;
    }
}
