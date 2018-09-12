package com.nekolr.upms.server.shiro.realm;

import com.nekolr.shiro.token.JwtToken;
import com.nekolr.util.JwtUtils;
import com.nekolr.util.StringHelper;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Map;
import java.util.Set;

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
        // 验证是否有资源访问的权限
        String payload = (String) principals.getPrimaryPrincipal();
        // payload 的格式为：jwt:{}
        if (payload.startsWith("jwt:") && payload.charAt(4) == '{' && payload.charAt(payload.length() - 1) == '}') {
            Map<String, Object> map = JwtUtils.readValue(payload);
            Set<String> roles = StringHelper.split2Set((String) map.get("roles"), ",");
            Set<String> perms = StringHelper.split2Set((String) map.get("perms"), ",");
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if (!roles.isEmpty()) {
                info.setRoles(roles);
            }
            if (!perms.isEmpty()) {
                info.setStringPermissions(perms);
            }
            return info;
        }
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
