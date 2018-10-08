package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.entity.OperationLog;
import com.nekolr.upms.provider.dao.OperationLogMapper;
import com.nekolr.upms.api.rpc.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
@com.alibaba.dubbo.config.annotation.Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

}
