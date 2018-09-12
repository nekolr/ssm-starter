package com.nekolr.upms.server.shiro.filter;


import com.nekolr.upms.api.rpc.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 过滤器链管理器
 *
 * @author nekolr
 */
@Component
public class FilterChainManager {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AccountService accountService;

    /**
     * 初始化默认的过滤器集合
     *
     * @return
     */
    public Map<String, Filter> initFilters() {
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // PasswordFilter
        PasswordFilter passwordFilter = new PasswordFilter(stringRedisTemplate);
        filterMap.put("auth", passwordFilter);
        // JwtFilter
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.setStringRedisTemplate(stringRedisTemplate);
        jwtFilter.setAccountService(accountService);
        filterMap.put("jwt", jwtFilter);
        return filterMap;
    }

    /**
     * 初始化过滤链规则
     *
     * @return
     */
    public Map<String, String> initFilterChainDefinitions() {
        Map<String, String> filterChain = new LinkedHashMap<>();
        // defaultAnon 为过滤器默认忽略的 URI
        List<String> defaultAnon = Arrays.asList();
        defaultAnon.forEach(e -> filterChain.put(e, "anon"));

        // defaultAuth 为需要 PasswordFilter 过滤器认证的 URI
        List<String> defaultAuth = Arrays.asList("/account/**");
        defaultAuth.forEach(e -> filterChain.put(e, "auth"));

        // 从数据库根据角色资源关系动态加载过滤链规则
        //TODO

        return filterChain;
    }
}
