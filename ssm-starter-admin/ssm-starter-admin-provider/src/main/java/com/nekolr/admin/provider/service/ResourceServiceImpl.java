package com.nekolr.admin.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.admin.api.entity.Resource;
import com.nekolr.admin.provider.dao.ResourceMapper;
import com.nekolr.admin.api.rpc.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
