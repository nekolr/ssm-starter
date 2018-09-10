package com.nekolr.upms.server.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nekolr.shiro.token.PasswordToken;
import com.nekolr.upms.api.entity.User;
import com.nekolr.upms.api.rpc.UserService;
import com.nekolr.util.EncryptUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 登录认证域
 *
 * @author nekolr
 */
public class PasswordRealm extends AuthorizingRealm {

    private UserService userService;

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
        if (!(token instanceof PasswordToken)) {
            return null;
        }
        String account = (String) token.getPrincipal();
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, account));
        if (user != null) {
            // 将明文密码加盐后进行 MD5 加密，然后放回 token
            ((PasswordToken) token).setPassword(EncryptUtils.md5(((PasswordToken) token).getPassword() + user.getSalt()));
            return new SimpleAuthenticationInfo(account, user.getPassword(), getName());
        } else {
            // 这样写需要保证前后端都进行一次登录时输入密码不为空的校验，不然任意账号输入空密码都可以通过了
            return new SimpleAuthenticationInfo(account, "", getName());
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
