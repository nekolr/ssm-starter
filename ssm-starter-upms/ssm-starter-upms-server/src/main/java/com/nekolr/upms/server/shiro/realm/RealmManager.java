package com.nekolr.upms.server.shiro.realm;

import org.apache.shiro.realm.Realm;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 域管理器
 *
 * @author nekolr
 */
@Component
public class RealmManager {

    public Collection<Realm> initRealms() {
        return null;
    }
}
