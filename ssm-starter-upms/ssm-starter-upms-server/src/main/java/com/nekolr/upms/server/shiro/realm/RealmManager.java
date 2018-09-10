package com.nekolr.upms.server.shiro.realm;

import com.nekolr.upms.api.rpc.UserService;
import com.nekolr.upms.server.shiro.matcher.JwtMatcher;
import com.nekolr.upms.server.shiro.matcher.PasswordMatcher;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

    public Collection<Realm> initRealms() {
        return null;
    }
}
