package org.javaboy.vhr.config;

import org.javaboy.vhr.model.Menu;
import org.javaboy.vhr.model.Role;
import org.javaboy.vhr.serve.MenuServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName MyFilter
 * @Author 周某
 * @Date 2020-04-22 22:33
 **/
/* 此类根据用户传递的请求地址,分析出请求需要的角色（权限访问） */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuServer menuServer;
    // 工具类：用来匹配url地址（访问的地址和数据库中的地址）
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();// 当前请求的地址
        List<Menu> menus = menuServer.getAllMenusWithRole(); // 获取跳转地址所需要的角色列表
        for (Menu menu : menus){
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) { // antPathMatcher.match(String pattern,String path) pattern 规则 用来做URL字符串匹配；
                List<Role> roles = menu.getRoles(); // 获取访问当前url所需要的角色
                String[] str = new String[roles.size()]; // SecurityConfig.createList() 参数为 字符串 ，所以要将roles转化为字符串
                for(int i=0; i<roles.size();i++){
                    str[i] = roles.get(i).getName();
                }
                // 对于匹配上的url，默认需要数据库中匹配的角色才能访问
                return SecurityConfig.createList(str);
            }
        }
        // 对于没有匹配上的url，默认需要ROLE_LOGIN权限，用来做标签。如：我们Controller层里面的url，在数据库中不存在
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
