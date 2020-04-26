package org.javaboy.vhr.serve;

import org.javaboy.vhr.mapper.MenuMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MenuServer
 * @Author 周某
 * @Date 2020-04-20 12:02
 **/
@Service
public class MenuServer {
    @Autowired
    MenuMapper menuMapper;

    /**
     * 查询当前用户所能访问的菜单数据
     * @return
     */
    public List<Menu> getMenusByHrId() {
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal() 当前用户登录的对象
        return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    /**
     * 查询menu表中访问url需要的所有role角色,因为此查询是频繁的且结果是固定的，所以我们可以添加一个缓存来缓存记录
     * @return
     */
//    @Cacheable
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

}
