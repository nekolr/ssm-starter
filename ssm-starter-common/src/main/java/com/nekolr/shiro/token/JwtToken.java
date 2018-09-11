package com.nekolr.shiro.token;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT Token
 *
 * @author nekolr
 */
@Getter
@Setter
public class JwtToken implements AuthenticationToken {

    /**
     * 用户账号
     */
    private String appId;

    /**
     * IP
     */
    private String ip;

    /**
     * 设备信息
     */
    private String deviceInfo;

    /**
     * 令牌
     */
    private String jwt;


    public JwtToken(String appId, String ip, String deviceInfo, String jwt) {
        this.appId = appId;
        this.ip = ip;
        this.deviceInfo = deviceInfo;
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return this.appId;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }
}
