package org.javaboy.vhr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Author 周某
 * @Date 2020-03-25 16:41
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/employee/basic/hello")
    public String hello02() {
        return "/employee/basic/**";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello03() {
        return "/employee/advanced/**";
    }

}
