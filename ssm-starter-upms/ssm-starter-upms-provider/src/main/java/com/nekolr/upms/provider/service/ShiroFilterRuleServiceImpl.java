package com.nekolr.upms.provider.service;

import com.nekolr.upms.api.rpc.ShiroFilterRuleService;
import com.nekolr.upms.common.vo.RoleResourceRule;
import com.nekolr.upms.provider.dao.ResourceMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Shiro 过滤链规则服务类实现
 *
 * @author nekolr
 */
public class ShiroFilterRuleServiceImpl implements ShiroFilterRuleService {

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public List<RoleResourceRule> getRoleResourceRuleList() {
        return resourceMapper.getRoleResourceRuleList();
    }
}
