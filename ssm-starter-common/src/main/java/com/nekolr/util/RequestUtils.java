package com.nekolr.util;

import com.nekolr.support.XssHttpServletRequestWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * ServletRequest 工具类
 *
 * @author nekolr
 */
public class RequestUtils {

    /**
     * 获取 HttpServletRequest 包装类
     *
     * @param request
     * @return
     */
    public static HttpServletRequest getXssHandleRequest(ServletRequest request) {
        return new XssHttpServletRequestWrapper((HttpServletRequest) request);
    }

    /**
     * 获取 parameter
     *
     * @param request
     * @param key
     * @return
     */
    public static String getParameter(ServletRequest request, String key) {
        return getXssHandleRequest(request).getParameter(key);
    }

}
