package com.nekolr.upms.common;

/**
 * UPMS 常量类
 *
 * @author nekolr
 */
public class UpmsConstants {

    // ----------------------------------------- 消息 -----------------------------------------

    /**
     * 匿名角色，此角色下分配的资源不需要权限即可访问
     */
    public static final String ROLE_ANON = "ROLE_ANON";

    /**
     * Redis 中存放的令牌过期时间，单位秒
     */
    public static final long REFRESH_PERIOD = 3600L;

    /**
     * 客户端收到的消息：无效的请求
     */
    public static final String ERROR_REQUEST_INFO = "error request";

    /**
     * 客户端收到的消息：无效的 JWT
     */
    public static final String ERROR_JWT_INFO = "error jwt";

    /**
     * 客户端收到的消息：过期的 JWT
     */
    public static final String EXPIRED_JWT_INFO = "expired jwt";

    /**
     * 客户端收到的消息：新的 JWT
     */
    public static final String NEW_JWT_INFO = "new jwt";

    /**
     * 客户端收到的消息：没有访问权限
     */
    public static final String NO_PERMISSION_INFO = "no permission";

    /**
     * 服务端内部使用的异常消息：过期的 JWT
     */
    public static final String EXPIRED_JWT_EXCEPTION_MESSAGE = "expiredJwt";

    /**
     * JWT 在 redis 中存放时的前缀
     */
    public static final String JWT_SESSION_PREFIX = "JWT_SESSION_";

    /**
     * JWT 签发者
     */
    public static final String ISSUER = "token-server";

    /**
     * 动态密钥放入 redis 中时，key 的前缀
     */
    public static final String TOKEN_KEY_PREFIX = "TOKEN_KEY_";

    /**
     * 客户端收到的消息：签发 tokenKey 成功
     */
    public static final String ISSUE_TOKEN_KEY_SUCCESS_INFO = "issue tokenKey success";

    /**
     * 客户端收到的消息：签发 tokenKey 失败
     */
    public static final String ISSUE_TOKEN_KEY_FAIL_INFO = "issue tokenKey fail";

    /**
     * 客户端收到的消息：登录失败
     */
    public static final String LOGIN_FAIL_INFO = "login fail";
}
