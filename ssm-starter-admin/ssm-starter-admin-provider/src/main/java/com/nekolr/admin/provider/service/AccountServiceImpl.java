package com.nekolr.admin.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.admin.api.rpc.AccountService;
import com.nekolr.admin.provider.dao.UserMapper;

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
