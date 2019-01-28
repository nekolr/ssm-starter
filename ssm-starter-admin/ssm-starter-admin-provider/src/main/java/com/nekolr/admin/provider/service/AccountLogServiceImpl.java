package com.nekolr.admin.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.admin.api.entity.AccountLog;
import com.nekolr.admin.provider.dao.AccountLogMapper;
import com.nekolr.admin.api.rpc.AccountLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 账户操作日志 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
@Service
public class AccountLogServiceImpl extends ServiceImpl<AccountLogMapper, AccountLog> implements AccountLogService {

}
