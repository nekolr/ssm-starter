package com.nekolr.admin.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.admin.api.entity.RoleResource;
import com.nekolr.admin.provider.dao.RoleResourceMapper;
import com.nekolr.admin.api.rpc.RoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 角色资源 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

}
