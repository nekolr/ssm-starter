package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.UserDO;
import com.nekolr.upms.provider.dao.UserMapper;
import com.nekolr.upms.api.rpc.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
