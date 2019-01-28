package com.nekolr.admin.provider.dao;

import com.nekolr.admin.api.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nekolr.admin.common.vo.RoleResourceRule;

import java.util.List;

/**
 * <p>
 * 资源 Mapper 接口
 * </p>
 *
 * @author nekolr
 * @since 2018-09-10
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 获取过滤链规则
     *
     * @return
     */
    List<RoleResourceRule> getRoleResourceRuleList();

    /**
     * 获取用户菜单
     *
     * @param appId
     * @return
     */
    List<Resource> getUserMenus(String appId);
}
