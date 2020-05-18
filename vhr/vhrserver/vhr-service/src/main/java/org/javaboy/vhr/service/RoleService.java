package org.javaboy.vhr.serve;

import org.javaboy.vhr.mapper.RoleMapper;
import org.javaboy.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleService
 * @Author 周某
 * @Date 2020-05-01 13:57
 **/
@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        // 判断前端传递过来的数据是否存在 ROLE_ 前缀，如果没有就将其添加上
        if (!role.getName().startsWith("ROLE_")) {
             role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
