package com.nekolr.upms.server.shiro.matcher;

import com.nekolr.upms.common.util.JwtUtils;
import com.nekolr.upms.common.vo.JwtAccount;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * JWT 匹配器
 *
 * @author nekolr
 */
@Component
public class JwtMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String jwt = (String) authenticationInfo.getCredentials();
        JwtAccount jwtAccount;
        try {
            jwtAccount = JwtUtils.parseJwt(jwt, SignatureAlgorithm.HS512, JwtUtils.SECRET_KEY);
        } catch (SecurityException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new AuthenticationException("errorJwt");
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("expiredJwt");
        } catch (Exception e) {
            throw new AuthenticationException("errorJwt");
        }
        if (jwtAccount == null) {
            throw new AuthenticationException("errorJwt");
        }
        return true;
    }
}
