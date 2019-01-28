package com.nekolr.admin.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.admin.api.entity.UserRole;
import com.nekolr.admin.provider.dao.UserRoleMapper;
import com.nekolr.admin.api.rpc.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
