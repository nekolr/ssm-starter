package com.nekolr.admin.server.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nekolr.admin.api.entity.Resource;
import com.nekolr.admin.api.rpc.MenuService;
import com.nekolr.admin.server.util.MenuUtils;
import com.nekolr.admin.server.vo.resource.Menu;
import com.nekolr.common.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Reference
    private MenuService menuService;

    @GetMapping("/{appId}")
    @ApiOperation(value = "获取用户菜单")
    @ApiImplicitParam(name = "appId", value = "账号", paramType = "query")
    public ResultBean<List<Menu>> getUserMenus(@PathVariable String appId) {
        List<Resource> menuList = menuService.getUserMenus(appId);
        List<Menu> menus = MenuUtils.dto2Vo(menuList);
        // 将菜单列表处理成带子节点的列表
        List<Menu> result = MenuUtils.menus2Tree(menus);
        return new ResultBean<>().setData(result);
    }
}
