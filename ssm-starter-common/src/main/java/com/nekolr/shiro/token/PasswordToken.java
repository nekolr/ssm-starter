package com.nekolr.shiro.token;

import com.nekolr.util.EncryptUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用户密码凭证
 *
 * @author nekolr
 */
@Getter
@Setter
public class PasswordToken implements AuthenticationToken {

    private String account;

    private String password;

    private String timestamp;

    private String ip;

    private String tokenKey;

    public PasswordToken(String account, String password, String timestamp, String ip, String tokenKey) {
        this.account = account;
        this.password = EncryptUtils.aesDecrypt(password, tokenKey);
        this.timestamp = timestamp;
        this.ip = ip;
        this.tokenKey = tokenKey;
    }

    @Override
    public Object getPrincipal() {
        return this.account;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }
}
