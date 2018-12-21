package com.nekolr.upms.server.shiro.realm;

import com.nekolr.upms.api.rpc.UserService;
import com.nekolr.upms.server.shiro.matcher.JwtMatcher;
import com.nekolr.upms.server.shiro.matcher.PasswordMatcher;
import com.nekolr.upms.server.shiro.token.JwtToken;
import com.nekolr.upms.server.shiro.token.PasswordToken;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 域管理器
 *
 * @author nekolr
 */
@Component
public class RealmManager {

    @Autowired
    private PasswordMatcher passwordMatcher;

    @Autowired
    private JwtMatcher jwtMatcher;

    @Autowired
    private UserService userService;

    /**
     * 初始化默认的 Realm 集合
     *
     * @return
     */
    public Collection<Realm> initRealms() {
        List<Realm> realmList = new LinkedList<>();
        // PasswordRealm
        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setUserService(userService);
        passwordRealm.setCredentialsMatcher(passwordMatcher);
        passwordRealm.setAuthenticationTokenClass(PasswordToken.class);
        realmList.add(passwordRealm);
        // JwtRealm
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        jwtRealm.setAuthenticationTokenClass(JwtToken.class);
        realmList.add(jwtRealm);
        return realmList;
    }
}
