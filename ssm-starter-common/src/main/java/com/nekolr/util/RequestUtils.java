package com.nekolr.util;

import com.alibaba.fastjson.JSON;
import com.nekolr.support.XssHttpServletRequestWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ServletRequest 工具类
 *
 * @author nekolr
 */
public class RequestUtils {

    private RequestUtils() {

    }

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

    /**
     * 获取整个请求的序列化结果
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestMap(ServletRequest request) {
        if (request.getAttribute("body") != null) {
            return (Map<String, String>) request.getAttribute("body");
        } else {
            try {
                // 序列化整个请求
                Map<String, String> body = JSON.parseObject(request.getInputStream(), Map.class);
                request.setAttribute("body", body);
                return body;
            } catch (IOException e) {
                e.printStackTrace();
                return new HashMap<>(0);
            }
        }
    }

}
