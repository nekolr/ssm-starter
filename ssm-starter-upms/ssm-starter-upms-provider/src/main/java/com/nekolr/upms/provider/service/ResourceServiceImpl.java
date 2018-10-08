package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.Resource;
import com.nekolr.upms.provider.dao.ResourceMapper;
import com.nekolr.upms.api.rpc.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
@com.alibaba.dubbo.config.annotation.Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
