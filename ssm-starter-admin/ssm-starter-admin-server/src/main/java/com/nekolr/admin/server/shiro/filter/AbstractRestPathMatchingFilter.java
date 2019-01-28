package com.nekolr.admin.server.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * REST 风格路径配置过滤器
 *
 * @author nekolr
 */
@Slf4j
public abstract class AbstractRestPathMatchingFilter extends PathMatchingFilter {

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
            return httpMethod.equals(pathPatterns[1].toUpperCase()) && pathsMatch(pathPatterns[0], requestURI);
        } else {
            log.error("PathPattern must be in the form like uri[==http method]");
            return false;
        }
    }

    /**
     * 获取 Subject
     *
     * @param request
     * @param response
     * @return
     */
    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }

    /**
     * 如果允许请求正常通过过滤器，则返回 true；
     * 如果请求应由 onAccessDenied(request,response,mappedValue) 方法处理，返回 false
     *
     * @param request
     * @param response
     * @param mappedValue 特定过滤器的 URL 映射
     * @return
     * @throws Exception
     */
    protected abstract boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception;

    /**
     * 根据 isAccessAllowed 方法确定拒绝 Subject 访问的请求
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return onAccessDenied(request, response);
    }

    /**
     * 根据 isAccessAllowed 方法确定拒绝 Subject 访问的请求
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception;

    /**
     * 返回请求最终是通过还是被拒绝
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return isAccessAllowed(request, response, mappedValue) || onAccessDenied(request, response, mappedValue);
    }

    protected void saveRequest(ServletRequest request) {
        WebUtils.saveRequest(request);
    }
}
