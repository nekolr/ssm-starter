package com.nekolr.upms.server.shiro.filter;

import com.nekolr.shiro.filter.AbstractRestPathMatchingFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public class JwtFilter extends AbstractRestPathMatchingFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }
}
