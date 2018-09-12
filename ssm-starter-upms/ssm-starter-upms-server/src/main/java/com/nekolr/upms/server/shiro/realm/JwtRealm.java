package com.nekolr.upms.server.shiro.realm;

import com.nekolr.shiro.token.JwtToken;
import com.nekolr.util.JwtUtils;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * JWT 域
 *
 * @author nekolr
 */
public class JwtRealm extends AuthorizingRealm {

    @Override
    public Class getAuthenticationTokenClass() {
        // 此域只支持 JwtToken
        return JwtToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof JwtToken)) {
            return null;
        }
        JwtToken jwtToken = (JwtToken) token;
        String jwt = jwtToken.getCredentials().toString();
        String payload;
        try {
            // 只解析 payload
            payload = JwtUtils.parsePayload(jwt);
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("errorJwt");
        } catch (Exception e) {
            throw new AuthenticationException("errorJwt");
        }
        if (payload == null) {
            throw new AuthenticationException("errorJwt");
        }
        return new SimpleAuthenticationInfo("jwt:" + payload, jwt, this.getName());
    }
}
