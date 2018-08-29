package com.nekolr.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;

/**
 * REST 风格路径配置过滤器
 *
 * @author nekolr
 */
@Slf4j
public class RESTPathMatchingFilter extends PathMatchingFilter {

    /**
     * 重写 URI 匹配规则，支持 HTTP METHOD
     *
     * @param path
     * @param request
     * @return
     */
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = getPathWithinApplication(request);
        String[] pathPatterns = path.split("==");
        log.trace("Attempting to match pattern '{}' with current requestURI '{}'...", path, requestURI);
        if (pathPatterns.length == 1) {
            // 只配置了 URI，没有配置请求方法，则直接匹配 URI
            return pathsMatch(pathPatterns[0], requestURI);
        } else if (pathPatterns.length == 2) {
            // 配置了 URI 和 HTTP METHOD，则需要两者都匹配
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();
            return httpMethod.equals(pathPatterns[1].toUpperCase()) && pathsMatch(pathPatterns[1], requestURI);
        } else {
            log.error("PathPattern must be in the form like uri[==http method]");
            return false;
        }
    }
}
