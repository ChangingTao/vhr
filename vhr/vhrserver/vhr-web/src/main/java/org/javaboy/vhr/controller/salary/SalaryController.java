package org.javaboy.vhr.controller.salary;

import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.Salary;
import org.javaboy.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.List;

/**
 * @ClassName SalaryController
 * @Author 周某
 * @Date 2020-05-18 17:13
 **/
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        if (salaryService.addSalary(salary) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalaryById(@PathVariable Integer id){
        if (salaryService.deleteSalaryById(id) == 1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateSalaryById(@RequestBody Salary salary){
        if(salaryService.updateSalaryById(salary) == 1 ){
            return RespBean.ok("更新成功！");
        }
            return RespBean.error("更新失败！");
    }

}
