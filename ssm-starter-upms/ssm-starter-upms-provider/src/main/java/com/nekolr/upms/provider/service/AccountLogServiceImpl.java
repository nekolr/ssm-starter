package com.nekolr.upms.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.upms.api.entity.AccountLog;
import com.nekolr.upms.provider.dao.AccountLogMapper;
import com.nekolr.upms.api.rpc.AccountLogService;
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
