package com.nekolr.upms.server.shiro.filter;

import com.nekolr.common.ResultBean;
import com.nekolr.shiro.filter.AbstractRestPathMatchingFilter;
import com.nekolr.shiro.token.JwtToken;
import com.nekolr.upms.api.rpc.AccountService;
import com.nekolr.upms.common.UpmsConstants;
import com.nekolr.upms.common.util.JwtUtils;
import com.nekolr.util.*;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * JWT 过滤器
 *
 * @author nekolr
 */
@Slf4j
public class JwtFilter extends AbstractRestPathMatchingFilter {

    /**
     * 客户端收到的消息：无效的请求
     */
    private static final String ERROR_REQUEST_INFO = "error request";

    /**
     * 客户端收到的消息：无效的 JWT
     */
    private static final String ERROR_JWT_INFO = "error jwt";

    /**
     * 客户端收到的消息：过期的 JWT
     */
    private static final String EXPIRED_JWT_INFO = "expired jwt";

    /**
     * 客户端收到的消息：新的 JWT
     */
    private static final String NEW_JWT_INFO = "new jwt";

    /**
     * 客户端收到的消息：没有访问权限
     */
    private static final String NO_PERMISSION_INFO = "no permission";

    /**
     * 服务端内部使用的异常消息：过期的 JWT
     */
    private static final String EXPIRED_JWT_EXCEPTION_MESSAGE = "expiredJwt";

    /**
     * JWT 在 redis 中存放时的前缀
     */
    private static final String JWT_SESSION_PREFIX = "JWT_SESSION_";

    /**
     * JWT 签发者
     */
    private static final String ISSUER = "token-server";

    private StringRedisTemplate stringRedisTemplate;

    private AccountService accountService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // subject 不能为空，且还未完成 JwtRealm 认证，且是 JWT 认证请求
        if (subject != null && !subject.isAuthenticated() && isJwtRequest(request)) {
            AuthenticationToken token = createJwtToken(request);
            try {
                // 使用 JwtToken 尝试验证身份
                subject.login(token);
                return this.checkRoles(subject, mappedValue);
            } catch (AuthenticationException e) {
                // JWT 过期
                if (EXPIRED_JWT_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                    String appId = ((HttpServletRequest) request).getHeader("appId");
                    String jwt = ((HttpServletRequest) request).getHeader("authorization");
                    String refreshJwt = stringRedisTemplate.opsForValue().get(JWT_SESSION_PREFIX + appId);
                    if (refreshJwt != null && refreshJwt.equals(jwt)) {
                        String roles = accountService.getUserRoles(appId);
                        String newJwt = JwtUtils.issueJwt(IdGenerator.randomUUID(), appId, ISSUER,
                                UpmsConstants.REFRESH_PERIOD >> 2, roles, null, SignatureAlgorithm.HS512);
                        // 将新令牌存入 Redis，key 的格式为：JWT_SESSION_appId
                        stringRedisTemplate.opsForValue().set(JWT_SESSION_PREFIX + appId, newJwt, UpmsConstants.REFRESH_PERIOD, TimeUnit.SECONDS);
                        // 返回新令牌
                        ResponseUtils.responseJson(response, new ResultBean().success(NEW_JWT_INFO).addData("jwt", newJwt));
                        return false;
                    } else {
                        // JWT 失效，同时刷新时间已过，需要提示客户端重新登录
                        ResponseUtils.responseJson(response, new ResultBean().fail(400, EXPIRED_JWT_INFO));
                        return false;
                    }
                }
                // 其他错误视为 JWT 无效
                ResponseUtils.responseJson(response, new ResultBean().fail(400, ERROR_JWT_INFO));
                return false;
            } catch (Exception e) {
                // 其他错误
                log.error("JWT 认证失败::{}::{}", IpUtils.getRemoteAddr((HttpServletRequest) request), token.toString(), e);
                ResponseUtils.responseJson(response, new ResultBean().fail(400, ERROR_JWT_INFO));
                return false;
            }
        } else {
            // 请求未携带 authorization，即 jwt，视为无效的请求
            ResponseUtils.responseJson(response, new ResultBean().fail(401, ERROR_REQUEST_INFO));
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);

        // 没有认证
        if (subject == null || !subject.isAuthenticated()) {
            // 需要客户端控制跳转到登录页面
            ResponseUtils.responseJson(response, new ResultBean().fail(400, ERROR_JWT_INFO));
        } else {
            // 已经认证但是没有权限访问
            ResponseUtils.responseJson(response, new ResultBean().fail(403, NO_PERMISSION_INFO));
        }

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

    /**
     * 检查当前用户是否具有 mappedValue 中的任意一个角色
     *
     * @param subject     主体（用户）
     * @param mappedValue 过滤链规则
     * @return
     */
    private boolean checkRoles(Subject subject, Object mappedValue) {
        String[] roles = (String[]) mappedValue;
        // mappedValue 为空会放行
        return roles == null || roles.length == 0 || Stream.of(roles).anyMatch(role -> subject.hasRole(role.trim()));
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
