package com.nekolr.support;

import com.nekolr.util.XssUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * 过滤 Xss 的 HttpServletRequest 包装类
 *
 * @author nekolr
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        Map<String, String[]> result = new HashMap<>((int) (((float) parameterMap.size() / 0.75F) + 1.0F));
        parameterMap.forEach((k, v) -> result.put(k, filterParams(v)));
        return result;
    }

    @Override
    public String getParameter(String name) {
        return filterParam(super.getParameter(name));
    }


    @Override
    public String[] getParameterValues(String name) {
        return filterParams(super.getParameterValues(name));
    }

    @Override
    public Cookie[] getCookies() {
        Cookie[] cookies = super.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                cookie.setValue(filterParam(cookie.getValue()));
            }
        }
        return cookies;
    }

    @Override
    public String getHeader(String name) {
        return filterParam(super.getHeader(name));
    }

    /**
     * 过滤参数数组
     *
     * @param params
     * @return
     */
    private String[] filterParams(String[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                params[i] = filterParam(params[i]);
            }
        }
        return params;
    }

    /**
     * 过滤参数
     *
     * @param param
     * @return
     */
    private String filterParam(String param) {
        if (null == param) {
            return null;
        }
        return XssUtils.filterXss(param);
    }
}
