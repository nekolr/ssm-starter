package com.nekolr.shiro.token;

import com.nekolr.util.EncryptUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用户密码凭证
 *
 * @author nekolr
 */
@Getter
@Setter
@ToString
public class PasswordToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * IP
     */
    private String ip;

    /**
     * 申请到的动态加密令牌
     */
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
