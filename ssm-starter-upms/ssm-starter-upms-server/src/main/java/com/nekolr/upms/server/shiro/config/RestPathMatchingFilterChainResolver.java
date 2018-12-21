package com.nekolr.upms.server.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Iterator;

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
        boolean isMatch;
        Iterator<String> iterator = filterChainManager.getChainNames().iterator();
        String pathPattern;
        String[] strings;
        do {
            if (!iterator.hasNext()) {
                return null;
            }
            pathPattern = iterator.next();
            strings = pathPattern.split("==");
            if (strings.length == 2) {
                // 比较 HTTP METHOD 是否一致，不一致就不匹配
                if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(strings[1].toUpperCase())) {
                    isMatch = true;
                } else {
                    isMatch = false;
                }
            } else {
                isMatch = false;
            }
            // 重新赋值
            pathPattern = strings[0];
        } while (!this.pathMatches(pathPattern, requestURI) || isMatch);

        if (log.isTraceEnabled()) {
            log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  " +
                    "Utilizing corresponding filter chain...");
        }
        if (strings.length == 2) {
            pathPattern = pathPattern.concat("==").concat(WebUtils.toHttp(request).getMethod().toUpperCase());
        }
        return filterChainManager.proxy(originalChain, pathPattern);
    }
}
