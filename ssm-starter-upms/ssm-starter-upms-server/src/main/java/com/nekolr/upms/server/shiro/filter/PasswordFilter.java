package com.nekolr.upms.server.shiro.filter;

import com.nekolr.common.ResultBean;
import com.nekolr.shiro.token.PasswordToken;
import com.nekolr.util.IpUtils;
import com.nekolr.util.RandomUtils;
import com.nekolr.util.RequestUtils;
import com.nekolr.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于用户名和密码的身份认证过滤器
 *
 * @author nekolr
 */
@Slf4j
public class PasswordFilter extends AccessControlFilter {

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 动态密钥放入 redis 中时，key 的前缀
     */
    private static final String TOKEN_KEY_PREFIX = "TOKEN_KEY_";

    public PasswordFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // 已经登录过
        if (subject != null && subject.isAuthenticated()) {
            return true;
        }
        // 拒绝，交给 onAccessDenied 处理
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        // 获取动态密钥的请求
        if (isGetTokenKey(request)) {
            return doGetTokenKey(request, response);
        }

        // 登录请求
        if (isLogin(request)) {
            return doLogin(request, response);
        }

        // 注册请求
        if (isRegister(request)) {
            // 直接通过，进入 controller 处理
            return true;
        }

        // 无效的请求
        ResponseUtils.responseJson(response, new ResultBean().fail(1000, ""));
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
     * 获取动态密钥
     *
     * @param request
     * @param response
     */
    private boolean doGetTokenKey(ServletRequest request, ServletResponse response) {
        String tokenKey = RandomUtils.randomString(16);
        String userKey = RandomUtils.randomString(6);
        String ip = IpUtils.getRemoteAddr((HttpServletRequest) request).toUpperCase();
        // 动态密钥放入 redis，有效期 5 秒，key 为 TOKEN_KEY_IP_userKey，value 为 tokenKey
        try {
            stringRedisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + ip + "_" + userKey, tokenKey, 5, TimeUnit.SECONDS);
            ResponseUtils.responseJson(response, new ResultBean().success("issue tokenKey success")
                    .addData("tokenKey", tokenKey).addData("userKey", userKey));
        } catch (Exception e) {
            log.warn("签发动态密钥失败：" + e.getMessage(), e);
            ResponseUtils.responseJson(response, new ResultBean().fail("issue tokenKey fail"));
        }

        return false;
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
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("POST")
                && account != null && password != null && method != null
                && timestamp != null && method.equals("login");
    }

    /**
     * 登录处理
     *
     * @param request
     * @param response
     */
    private boolean doLogin(ServletRequest request, ServletResponse response) {
        // 根据提交的信息创建 token
        AuthenticationToken token = createPasswordToken(request);
        Subject subject = getSubject(request, response);
        try {
            // 尝试登录
            subject.login(token);
            // 登录认证成功
            return true;
        } catch (AuthenticationException e) {
            log.warn(token.getPrincipal() + "::" + e.getMessage());
            ResponseUtils.responseJson(response, new ResultBean().fail(403, "login fail"));
        } catch (Exception e) {
            log.warn(token.getPrincipal() + "::认证异常::" + e.getMessage(), e);
            ResponseUtils.responseJson(response, new ResultBean().fail(403, "login fail"));
        }
        return false;
    }

    /**
     * 是否是注册请求
     *
     * @param request
     * @return
     */
    private boolean isRegister(ServletRequest request) {
        return false;
    }

    /**
     * 创建 PasswordToken
     *
     * @param request
     * @return
     */
    private AuthenticationToken createPasswordToken(ServletRequest request) {
        Map<String, String> body = RequestUtils.getRequestMap(request);
        String account = body.get("account");
        String password = body.get("password");
        String timestamp = body.get("timestamp");
        String ip = IpUtils.getRemoteAddr((HttpServletRequest) request).toUpperCase();
        String userKey = body.get("userKey");
        String tokenKey = stringRedisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + ip + "_" + userKey);
        return new PasswordToken(account, password, timestamp, ip, tokenKey);
    }
}
