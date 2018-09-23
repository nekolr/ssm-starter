package com.nekolr.upms.server.controller;

import com.nekolr.upms.api.dto.ResourceDTO;
import com.nekolr.upms.api.rpc.MenuService;
import com.nekolr.upms.server.vo.resource.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author nekolr
 */
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping()
    public ResponseEntity<Menu> getUserMenus(String appId) {
        if (StringUtils.isEmpty(appId)) {

        } else {
            List<ResourceDTO> menuList = menuService.getUserMenus(appId);

        }
    }
}
