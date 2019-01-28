package com.nekolr.admin.api.rpc;

import com.nekolr.admin.common.vo.RoleResourceRule;

import java.util.List;

/**
 * Shiro 过滤链规则服务类
 *
 * @author nekolr
 */
public interface ShiroFilterRuleService {

    /**
     * 获取过滤链规则
     *
     * @return
     */
    List<RoleResourceRule> getRoleResourceRuleList();
}
