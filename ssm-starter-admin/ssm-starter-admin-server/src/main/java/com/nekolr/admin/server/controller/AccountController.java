package com.nekolr.admin.server.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nekolr.admin.api.entity.User;
import com.nekolr.admin.api.rpc.AccountService;
import com.nekolr.admin.api.rpc.UserService;
import com.nekolr.admin.common.AdminConstants;
import com.nekolr.admin.common.util.JwtUtils;
import com.nekolr.admin.server.support.factory.LogTaskFactory;
import com.nekolr.admin.server.support.factory.manager.LogExecuteManager;
import com.nekolr.admin.server.vo.user.Account;
import com.nekolr.common.ResultBean;
import com.nekolr.common.ResultCode;
import com.nekolr.util.IdGenerator;
import com.nekolr.util.IpUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/account")
@Api
public class AccountController {

    @Autowired
    private LogExecuteManager logExecuteManager;

    @Reference
    private AccountService accountService;

    @Reference
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "账号登录")
    @PostMapping("/login")
    public ResultBean<Account> login(HttpServletRequest request, @NotNull String account) {
        // 获取用户对应的角色
        String roles = accountService.getUserRoles(account);
        // 获取用户信息
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, account));
        // 签发 Token
        String jwt = JwtUtils.issueJwt(IdGenerator.randomUUID(), account, AdminConstants.ISSUER, AdminConstants.REFRESH_PERIOD >> 2,
                roles, null, SignatureAlgorithm.HS512);
        // 放入 Redis
        stringRedisTemplate.opsForValue().set(AdminConstants.JWT_SESSION_PREFIX + account, jwt, AdminConstants.REFRESH_PERIOD, TimeUnit.SECONDS);
        // 记录日志
        logExecuteManager.executeLogTask(LogTaskFactory.loginLog(account, IpUtils.getRemoteAddr(request), true, "登录成功"));
        // 组装账户 VO
        Account entity = new Account();
        entity.setAccount(account);
        entity.setJwt(jwt);
        entity.setAvatar(user.getAvatar());

        return new ResultBean<>().setData(entity).setCode(ResultCode.SUCCESS.getCode()).setMessage(ResultCode.SUCCESS.getMessage());
    }

}
