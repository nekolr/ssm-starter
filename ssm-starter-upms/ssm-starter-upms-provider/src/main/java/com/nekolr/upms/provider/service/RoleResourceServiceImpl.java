package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.RoleResource;
import com.nekolr.upms.provider.dao.RoleResourceMapper;
import com.nekolr.upms.api.rpc.RoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
