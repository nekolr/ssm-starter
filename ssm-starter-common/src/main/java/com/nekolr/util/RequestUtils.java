package com.nekolr.util;

import com.nekolr.support.XssHttpServletRequestWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 工具类
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


}
