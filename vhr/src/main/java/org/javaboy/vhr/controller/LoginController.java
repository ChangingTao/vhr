package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.RespBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginControll
 * @Author 周某
 * @Date 2020-03-25 18:23
 **/
@RestController
public class LoginController {
//    @CrossOrigin
    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录，请进行登录");
    }

}
