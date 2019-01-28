package com.nekolr.admin.provider.dao;

import com.nekolr.admin.api.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author nekolr
 * @since 2018-09-09
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户的所有角色
     *
     * @param appId 用户标识（可以是用户名、邮箱等）
     * @return
     */
    String getUserRoles(String appId);
}
