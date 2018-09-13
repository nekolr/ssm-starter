package com.nekolr.upms.common.vo;

import com.nekolr.upms.common.UpmsConstant;
import com.nekolr.util.StringHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Set;

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

    /**
     * 将 URI 和 roles 转化为过滤链规则
     *
     * @return
     */
    public String toFilterChainRule() {
        if (!StringUtils.isEmpty(this.uri) && !StringUtils.isEmpty(this.roles)) {
            Set<String> roleSet = StringHelper.split2Set(this.roles, ",");
            /**
             * 此处约定为：
             * - 如果一个资源被分配到 role_anon 角色下，则该资源不需要认证和权限即可访问
             * - 除此之外的角色下的资源都需要认证和授权才可以访问
             */
            if (roleSet.contains(UpmsConstant.ROLE_ANON)) {
                return "anon";
            } else {
                return "jwt:[" + this.roles + "]";
            }
        }
        return null;
    }
}
