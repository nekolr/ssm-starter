package com.nekolr.admin.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.nekolr.admin.api.rpc.ShiroFilterRuleService;
import com.nekolr.admin.common.vo.RoleResourceRule;
import com.nekolr.admin.provider.dao.ResourceMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Shiro 过滤链规则服务类实现
 *
 * @author nekolr
 */
@Service
public class ShiroFilterRuleServiceImpl implements ShiroFilterRuleService {

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public List<RoleResourceRule> getRoleResourceRuleList() {
        return resourceMapper.getRoleResourceRuleList();
    }
}
