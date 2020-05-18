package org.javaboy.vhr.controller.sys;

import io.swagger.models.auth.In;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.Role;
import org.javaboy.vhr.serve.HrService;
import org.javaboy.vhr.serve.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HrController
 * @Author 周某
 * @Date 2020-05-13 17:49
 **/
@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    HrService hrService;

    @Autowired
    RoleService roleService;

    /* 获取（除自己外的）所有用户 */
    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords) {
        return hrService.getAllHrs(keywords);
    }



    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
        if (hrService.updateHr(hr) == 1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /* 获取所有的角色 */
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    /* 修改用户角色信息 */
    @PutMapping("/role")
    public RespBean updateHrRoles(Integer hrId, Integer[] rids){
        if (hrService.updateHrRoles(hrId, rids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id){
        if (hrService.deleteHrById(id)==1){
            return  RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
