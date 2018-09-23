package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.dto.ResourceDTO;
import com.nekolr.upms.api.rpc.MenuService;
import com.nekolr.upms.provider.dao.ResourceMapper;

import javax.annotation.Resource;
import java.util.List;

public class MenuServiceImpl implements MenuService {

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public List<ResourceDTO> getUserMenus(String appId) {
        return resourceMapper.getUserMenus(appId);
    }
}
