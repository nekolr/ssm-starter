package com.nekolr.upms.server.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 账户 VO
 *
 * @author nekolr
 */
@Data
public class Account implements Serializable {

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
     * 盐
     */
    private String salt;
}
