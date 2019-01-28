package com.nekolr.admin.api.rpc;

/**
 * 账号服务类
 *
 * @author nekolr
 */
public interface AccountService {

    /**
     * 获取用户的所有角色
     *
     * @param appId 用户标识（可以是用户名、邮箱等）
     * @return
     */
    String getUserRoles(String appId);
}
