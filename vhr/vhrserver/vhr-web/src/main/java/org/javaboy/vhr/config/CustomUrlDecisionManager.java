package org.javaboy.vhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @ClassName MyDesicionManager
 * @Author 周某
 * @Date 2020-04-23 19:11
 **/
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    /**
     * @param authentication   成功后返回的信息(用户的信息)
     * @param object           请求对象
     * @param configAttributes MyFilter中Collection<ConfigAttribute>返回的角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) { // 遍历MyFilter类中通过SecurityConfig.createList()配置的角色
            String needRole = configAttribute.getAttribute(); // 获取需要的角色
            if ("ROLE_LOGIN".equals(needRole)) {
                // 判断是否有登录
                if (authentication instanceof AnonymousAuthenticationToken) { // AnonymousAuthenticationToken:匿名身份验证令牌 用来判断是否登录
                    throw new AccessDeniedException("尚未登录，请登录");
                }else{
                    return;
                }
            }
            // authoritiesRoles: 获取当前用户所具有的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 当前链接访问需要 A & B 两个角色，只需要你具有其中一个角色即可访问
            for (GrantedAuthority authoritiesRole : authorities) {
                if (authoritiesRole.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
