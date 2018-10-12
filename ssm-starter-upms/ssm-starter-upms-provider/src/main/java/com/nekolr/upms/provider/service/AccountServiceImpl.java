package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.rpc.AccountService;
import com.nekolr.upms.provider.dao.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 账号服务类实现
 *
 * @author nekolr
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String getUserRoles(String appId) {
        return userMapper.getUserRoles(appId);
    }
}
