package com.nekolr.upms.server.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 账户 VO
 *
 * @author nekolr
 */
@Data
@ApiModel(value = "Account 对象", description = "账户 VO")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty(value = "盐")
    private String salt;

    /**
     * 令牌
     */
    @ApiModelProperty(value = "令牌")
    private String jwt;
}
