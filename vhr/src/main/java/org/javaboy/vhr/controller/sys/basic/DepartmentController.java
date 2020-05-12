package org.javaboy.vhr.controller.sys.basic;

import io.swagger.models.auth.In;
import org.javaboy.vhr.model.Department;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.serve.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName DepartmentController
 * @Author 周某
 * @Date 2020-05-01 17:08
 **/
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    // 递归进行查询数据
    @GetMapping("/")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    // 添加部门
    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep) {
        departmentService.addDep(dep);
        if (dep.getResult() == 1) {
            return RespBean.ok("添加成功！", dep);
        }
        return RespBean.error("添加失败！");
    }

    // 删除部门
    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult() == -2) {
            return RespBean.error("该部门下存在子部门，无法删除！");
        } else if (dep.getResult() == -1) {
            return RespBean.error("该部门下存在员工，无法删除！");
        } else if (dep.getResult() == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
