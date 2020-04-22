package org.javaboy.vhr.serve;

import org.javaboy.vhr.mapper.MenuMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Menu> getMenusByHrId(){
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal() 当前用户登录的对象
        return menuMapper.getMenusByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

}
