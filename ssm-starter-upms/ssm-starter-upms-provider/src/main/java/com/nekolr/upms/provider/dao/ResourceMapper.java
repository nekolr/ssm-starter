package com.nekolr.upms.provider.dao;

import com.nekolr.upms.api.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nekolr.upms.common.vo.RoleResourceRule;

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
}
