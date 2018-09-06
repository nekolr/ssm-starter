package com.nekolr.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用户密码凭证
 *
 * @author nekolr
 */
public class PasswordToken implements AuthenticationToken {
    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
