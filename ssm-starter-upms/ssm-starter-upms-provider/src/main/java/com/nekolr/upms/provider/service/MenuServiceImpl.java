package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.dto.ResourceDTO;
import com.nekolr.upms.api.rpc.MenuService;
import com.nekolr.upms.provider.dao.ResourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public List<ResourceDTO> getUserMenus(String appId) {
        return resourceMapper.getUserMenus(appId);
    }
}
