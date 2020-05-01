package org.javaboy.vhr.serve;

import org.javaboy.vhr.mapper.MenuMapper;
import org.javaboy.vhr.mapper.MenuRoleMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName MenuServer
 * @Author 周某
 * @Date 2020-04-20 12:02
 **/
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

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

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    // 添加事务操作
    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        // 先将当前用户删除再插入当前用户权限信息
        menuRoleMapper.deleteById(rid);
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result == mids.length;
    }
}
