package com.nekolr.upms.server.shiro.filter;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 基于用户名和密码的身份认证过滤器
 *
 * @author nekolr
 */
@Slf4j
public class PasswordFilter extends AccessControlFilter {

    private StringRedisTemplate stringRedisTemplate;

    public PasswordFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // 已经登录过
        if (ObjectUtils.isNotNull(subject) && subject.isAuthenticated()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return false;
    }
}
