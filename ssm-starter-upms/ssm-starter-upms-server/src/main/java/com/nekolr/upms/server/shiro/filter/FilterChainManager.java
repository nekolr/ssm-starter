package com.nekolr.upms.server.shiro.filter;


import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.Map;

/**
 * 过滤器链管理器
 *
 * @author nekolr
 */
@Component
public class FilterChainManager {

    public Map<String, Filter> initFilters() {
        return null;
    }

    public Map<String, String> initFilterChain() {
        return null;
    }
}
