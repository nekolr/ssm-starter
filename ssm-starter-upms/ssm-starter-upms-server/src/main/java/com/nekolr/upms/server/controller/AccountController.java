package com.nekolr.upms.server.controller;

import com.nekolr.upms.api.rpc.AccountService;
import com.nekolr.upms.common.UpmsConstants;
import com.nekolr.upms.common.util.JwtUtils;
import com.nekolr.upms.server.support.factory.LogTaskFactory;
import com.nekolr.upms.server.support.factory.manager.LogExecuteManager;
import com.nekolr.upms.server.vo.user.Account;
import com.nekolr.util.IdGenerator;
import com.nekolr.util.IpUtils;
import com.nekolr.util.RequestUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/account")
@Api
public class AccountController {

    @Resource
    private AccountService accountService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "账号登录")
    @PostMapping("/login")
    public ResponseEntity<Account> login(HttpServletRequest request) {
        // 获取账号
        String account = RequestUtils.getParameter(request, "account");
        // 获取用户对应的角色
        String roles = accountService.getUserRoles(account);
        // 签发 Token
        String jwt = JwtUtils.issueJwt(IdGenerator.randomUUID(), account, UpmsConstants.ISSUER, UpmsConstants.REFRESH_PERIOD >> 2,
                roles, null, SignatureAlgorithm.HS512);
        // 放入 Redis
        stringRedisTemplate.opsForValue().set(UpmsConstants.JWT_SESSION_PREFIX + account, jwt, UpmsConstants.REFRESH_PERIOD, TimeUnit.SECONDS);
        // 记录日志
        LogExecuteManager.getInstance().executeLogTask(LogTaskFactory.loginLog(account, IpUtils.getRemoteAddr(request), true, "登录成功"));
        // 组装账户 VO
        Account entity = new Account();
        entity.setAccount(account);
        entity.setJwt(jwt);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

}
