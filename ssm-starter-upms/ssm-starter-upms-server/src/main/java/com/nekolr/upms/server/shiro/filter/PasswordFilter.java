package com.nekolr.upms.server.shiro.filter;

import com.nekolr.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 基于用户名和密码的身份认证过滤器
 *
 * @author nekolr
 */
@Slf4j
public class PasswordFilter extends AccessControlFilter {

    private StringRedisTemplate stringRedisTemplate;

    public PasswordFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // 已经登录过
        if (subject != null && subject.isAuthenticated()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return false;
    }

    /**
     * 是否是获取动态加密密钥的请求，获取到的密钥用于前端登录密码的解密
     *
     * @param request
     * @return
     */
    private boolean isGetTokenKey(ServletRequest request) {
        String tokenKey = RequestUtils.getParameter(request, "tokenKey");
        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("GET")
                && tokenKey != null && "get".equals(tokenKey);
    }

    /**
     * 是否是登录请求
     *
     * @param request
     * @return
     */
    private boolean isLogin(ServletRequest request) {
        Map<String, String> body = RequestUtils.getRequestMap(request);
        String account = body.get("account");
        String password = body.get("password");
        String timestamp = body.get("timestamp");
        String method = body.get("method");
        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest)request).getMethod().toUpperCase().equals("POST")
                && account != null && password != null && method != null
                && timestamp != null && method.equals("login");
    }
}
