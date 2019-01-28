package com.nekolr.admin.server.shiro.filter;


import com.alibaba.dubbo.config.annotation.Reference;
import com.nekolr.common.Constants;
import com.nekolr.admin.api.rpc.AccountService;
import com.nekolr.admin.api.rpc.ShiroFilterRuleService;
import com.nekolr.admin.common.vo.RoleResourceRule;
import com.nekolr.admin.server.config.bean.ShiroBean;
import com.nekolr.util.EnvironmentUtils;
import com.nekolr.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Reference
    private AccountService accountService;

    @Reference
    private ShiroFilterRuleService shiroFilterRuleService;

    @Autowired
    private Environment environment;

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
    public Map<String, String> initFilterChainDefinitions() throws Exception {

        ShiroBean shiroBean = EnvironmentUtils.toBean(this.environment, ShiroBean.class);

        Map<String, String> filterChain = new LinkedHashMap<>();

        // defaultAuth 为需要 PasswordFilter 过滤器认证的 URI
        List<String> defaultAuth = Arrays.asList("/account/**", "/druid/**");
        defaultAuth.forEach(auth -> filterChain.put(auth, "auth"));

        // defaultAnon 为过滤器默认忽略的 URI
        List<String> defaultAnon = Arrays.asList(StringHelper.split(shiroBean.getIgnoredUris(), Constants.SPLIT_SEPARATOR));
        defaultAnon.forEach(anon -> filterChain.put(anon, "anon"));

        // 从数据库根据角色资源关系动态加载过滤链规则
        if (shiroFilterRuleService != null) {
            List<RoleResourceRule> roleResourceRules = shiroFilterRuleService.getRoleResourceRuleList();
            if (roleResourceRules != null) {
                roleResourceRules.forEach(rule -> {
                    String chain = rule.toFilterChainRule();
                    if (chain != null) {
                        filterChain.putIfAbsent(rule.getUri(), chain);
                    }
                });
            }
        }

        return filterChain;
    }
}
