package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.UserRole;
import com.nekolr.upms.provider.dao.UserRoleMapper;
import com.nekolr.upms.api.rpc.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
