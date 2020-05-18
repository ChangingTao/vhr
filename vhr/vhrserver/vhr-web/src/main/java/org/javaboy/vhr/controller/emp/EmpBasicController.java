package org.javaboy.vhr.controller.emp;

import io.swagger.models.auth.In;
import org.javaboy.vhr.model.*;
import org.javaboy.vhr.serve.*;
import org.javaboy.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName EmpBasicController
 * @Author 周某
 * @Date 2020-05-14 10:13
 **/
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    NationService nationService;

    @Autowired
    PoliticsstatusService politicsstatusService;

    @Autowired
    JobLevelService jobLevelService;

    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

    // 获取所有员工信息（带有分页功能）
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                          Employee employee, Date[] beginDateScope) {
        return employeeService.getEmployeeByPage(page, size, employee, beginDateScope);
    }

    // 添加员工
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        if (employeeService.addEmp(employee) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    // 删除员工
    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id) {
        if (employeeService.deleteEmpByEid(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    // 更新员工
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateEmp(employee) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    // 民族
    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.getAllNations();
    }

    // 政治面貌
    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusService.getAllPoliticsstatus();
    }

    // 职称
    @GetMapping("/jobLevels")
    public List<JobLevel> getAllJobLevel() {
        return jobLevelService.getAllJobLevels();
    }

    // 职位
    @GetMapping("/positions")
    public List<Position> getAllPosition() {
        return positionService.getAllPositions();
    }

    // 工号
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID() {
        RespBean respBean = RespBean.build().setStatus(200)
                .setObj(String.format("%08d", employeeService.maxWorkID() + 1));
        return respBean;
    }

    // 所属部门
    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartment();
    }

    // Excel操作：导出
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() {
        List<Employee> list = (List<Employee>) employeeService.getEmployeeByPage(null, null, null, null).getData();
        return POIUtils.employee2Excel(list);
    }

    // Excel操作：文件上传
    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        List<Employee> list = POIUtils.excel2Employee(
                file,
                nationService.getAllNations(),
                politicsstatusService.getAllPoliticsstatus(),
                departmentService.getAllDepartmentWithOutChildren(),
                positionService.getAllPositions(),
                jobLevelService.getAllJobLevels());
        if(employeeService.addEmps(list) == list.size()){
            return RespBean.ok("插入成功！");
        }
//        file.transferTo(new File("E:\\javaboy.xls"));
        return RespBean.ok("插入失败！");
    }
}























