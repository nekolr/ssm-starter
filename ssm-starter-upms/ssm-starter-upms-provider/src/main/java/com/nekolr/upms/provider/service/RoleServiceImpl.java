package com.nekolr.upms.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.upms.api.entity.Role;
import com.nekolr.upms.provider.dao.RoleMapper;
import com.nekolr.upms.api.rpc.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
