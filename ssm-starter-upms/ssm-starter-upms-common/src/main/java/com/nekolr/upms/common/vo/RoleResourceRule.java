package com.nekolr.upms.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 角色资源过滤链规则
 *
 * @author nekolr
 */
@Getter
@Setter
@ToString
public class RoleResourceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源的 URI
     */
    private String uri;

    /**
     * 访问资源需要的角色，例如：admin,employee,leader
     */
    private String roles;
}
