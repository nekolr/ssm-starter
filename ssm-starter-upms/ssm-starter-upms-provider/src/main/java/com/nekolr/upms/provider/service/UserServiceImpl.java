package com.nekolr.upms.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.upms.api.entity.User;
import com.nekolr.upms.provider.dao.UserMapper;
import com.nekolr.upms.api.rpc.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
