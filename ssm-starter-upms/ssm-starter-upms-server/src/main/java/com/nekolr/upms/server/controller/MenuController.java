package com.nekolr.upms.server.controller;

import com.nekolr.upms.server.vo.resource.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @GetMapping()
    public ResponseEntity<Menu> getUserMenus(@RequestParam(name = "appId") @NotEmpty String appId) {
        return null;
    }
}
