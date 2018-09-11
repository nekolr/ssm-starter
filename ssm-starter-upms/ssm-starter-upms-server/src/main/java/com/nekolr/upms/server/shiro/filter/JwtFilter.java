package com.nekolr.upms.server.shiro.filter;

import com.nekolr.shiro.filter.AbstractRestPathMatchingFilter;
import com.nekolr.shiro.token.JwtToken;
import com.nekolr.util.IpUtils;
import com.nekolr.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * JWT 过滤器
 *
 * @author nekolr
 */
@Slf4j
public class JwtFilter extends AbstractRestPathMatchingFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        // subject 不能为空，且已经经过了 PasswordFilter 登录认证，且是 JWT 认证请求
        if (subject != null && subject.isAuthenticated() && isJwtRequest(request)) {
            AuthenticationToken token = createJwtToken(request);
            try {
                subject.login(token);
            } catch (AuthenticationException e) {
                // TODO 处理自定义的错误消息
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 判断是否是 JWT 认证请求
     *
     * @param request
     * @return
     */
    private boolean isJwtRequest(ServletRequest request) {
        String jwt = RequestUtils.getHeader(request, "authorization");
        String appId = RequestUtils.getHeader(request, "appId");
        return (request instanceof HttpServletRequest) && !StringUtils.isEmpty(jwt) && !StringUtils.isEmpty(appId);
    }

    /**
     * 创建 JWT 请求令牌
     *
     * @param request
     * @return
     */
    private AuthenticationToken createJwtToken(ServletRequest request) {
        Map<String, String> headers = RequestUtils.getHeaders(request);
        String appId = headers.get("appId");
        String ip = IpUtils.getRemoteAddr((HttpServletRequest) request);
        String jwt = headers.get("authorization");
        String deviceInfo = headers.get("deviceInfo");
        return new JwtToken(appId, ip, deviceInfo, jwt);
    }
}
