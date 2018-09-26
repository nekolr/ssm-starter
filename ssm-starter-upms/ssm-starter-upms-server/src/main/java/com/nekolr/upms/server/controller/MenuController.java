package com.nekolr.upms.server.controller;

import com.nekolr.upms.api.entity.Resource;
import com.nekolr.upms.api.rpc.MenuService;
import com.nekolr.upms.server.util.MenuUtils;
import com.nekolr.upms.server.vo.resource.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author nekolr
 */
@RestController
@RequestMapping("/menus")
@Api
public class MenuController {

    @javax.annotation.Resource
    private MenuService menuService;

    @GetMapping("/getUserMenus")
    @ApiOperation(value = "获取用户菜单")
    @ApiImplicitParam(name = "appId", value = "账号", paramType = "query")
    public ResponseEntity<List<Menu>> getUserMenus(@NotBlank String appId) {
        List<Resource> menuList = menuService.getUserMenus(appId);
        List<Menu> menus = MenuUtils.dto2Vo(menuList);
        // 将菜单列表处理成带子节点的列表
        List<Menu> result = MenuUtils.menus2Tree(menus);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
