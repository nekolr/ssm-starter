package com.nekolr.admin.common.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * JWT 账户，从令牌中解析出有效荷载后存入该实体
 *
 * @author nekolr
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 令牌 Id
     */
    private String tokenId;

    /**
     * 用户标识（账户或者是用户名等）
     */
    private String appId;

    /**
     * 签发者
     */
    private String issuer;

    /**
     * 签发时间
     */
    private Date issuedAt;

    /**
     * 接收者
     */
    private String audience;

    /**
     * 角色
     */
    private String roles;

    /**
     * 权限（资源）
     */
    private String perms;

    /**
     * 用户地址（IP）
     */
    private String host;

}
