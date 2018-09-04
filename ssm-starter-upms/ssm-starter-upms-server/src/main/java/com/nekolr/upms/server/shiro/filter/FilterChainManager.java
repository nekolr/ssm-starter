package com.nekolr.upms.server.shiro.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器链管理器
 *
 * @author nekolr
 */
@Component
public class FilterChainManager {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public FilterChainManager(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public Map<String, Filter> initFilters() {
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // PasswordFilter
        PasswordFilter passwordFilter = new PasswordFilter(stringRedisTemplate);
        filterMap.put("auth", passwordFilter);
        // JwtFilter
        JwtFilter jwtFilter = new JwtFilter();
        filterMap.put("jwt", jwtFilter);
        return filterMap;
    }

    public Map<String, String> initFilterChainDefinitions() {
        return null;
    }
}
