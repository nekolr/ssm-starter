package com.nekolr.admin.api.rpc;

import com.nekolr.admin.api.entity.Resource;

import java.util.List;

/**
 * 菜单服务类
 *
 * @author nekolr
 */
public interface MenuService {

    /**
     * 获取用户菜单
     *
     * @param appId
     * @return
     */
    List<Resource> getUserMenus(String appId);
}
