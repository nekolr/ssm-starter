package com.nekolr.upms.common;

/**
 * UPMS 常量类
 *
 * @author nekolr
 */
public class UpmsConstants {

    /**
     * 匿名角色，此角色下分配的资源不需要权限即可访问
     */
    public static final String ROLE_ANON = "ROLE_ANON";

    /**
     * Redis 中存放的令牌过期时间，单位秒
     */
    public static final long REFRESH_PERIOD = 3600L;
}
