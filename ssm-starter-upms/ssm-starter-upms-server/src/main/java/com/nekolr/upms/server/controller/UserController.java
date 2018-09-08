package com.nekolr.upms.server.controller;


import com.nekolr.upms.api.rpc.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author nekolr
 * @since 2018-09-08
 */
@RestController
@RequestMapping("/upms/user")
public class UserController {

    @Resource
    private UserService userService;

    public void get() {
        userService.getDTOById("");
    }
}

