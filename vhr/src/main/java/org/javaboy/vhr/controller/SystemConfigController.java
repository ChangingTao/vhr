package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Menu;
import org.javaboy.vhr.serve.MenuServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SystemConfigController
 * @Author 周某
 * @Date 2020-04-20 11:59
 **/
// 系统配置Controller
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuServer menuServer;

    // 前端数据不可行，所以数据方面
    @GetMapping("/menu")
    public List<Menu> getMenusByHrId() {
        return menuServer.getMenusByHrId();
    }
}
