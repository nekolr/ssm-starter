package com.nekolr.upms.server.shiro.realm;

import com.nekolr.shiro.token.PasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 登录认证域
 *
 * @author nekolr
 */
public class PasswordRealm extends AuthorizingRealm {

    @Override
    public Class getAuthenticationTokenClass() {
        // 只支持 PasswordToken
        return PasswordToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 此域只需要认证登录，后续的请求鉴权在 JwtRealm 进行
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }
}
