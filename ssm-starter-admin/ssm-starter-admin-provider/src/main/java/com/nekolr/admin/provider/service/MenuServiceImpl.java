package com.nekolr.admin.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.admin.api.entity.Resource;
import com.nekolr.admin.api.rpc.MenuService;
import com.nekolr.admin.provider.dao.ResourceMapper;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getUserMenus(String appId) {
        return resourceMapper.getUserMenus(appId);
    }
}
