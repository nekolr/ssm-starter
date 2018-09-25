package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.Resource;
import com.nekolr.upms.api.rpc.MenuService;
import com.nekolr.upms.provider.dao.ResourceMapper;
import org.springframework.stereotype.Service;

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
