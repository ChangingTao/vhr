package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ChatController
 * @Author 周某
 * @Date 2020-05-20 9:06
 **/
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    org.javaboy.vhr.serve.HrService hrService;

    @GetMapping("/hrs")
    public List<Hr> getAllHrs(){
        return hrService.getAllHrsExceptCurrentHr();
    }

}
