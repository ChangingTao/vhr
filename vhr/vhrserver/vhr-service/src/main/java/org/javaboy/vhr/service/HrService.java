package org.javaboy.vhr.serve;

import io.swagger.models.auth.In;
import org.javaboy.vhr.mapper.HrMapper;
import org.javaboy.vhr.mapper.HrRoleMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName UserService
 * @Author 周某
 * @Date 2020-03-25 16:22
 **/
@Service
public class HrService implements UserDetailsService {

    @Autowired
    HrMapper hrMapper;

    @Autowired
    HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {
        // 获取当前角色的Id
        return  hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(), keywords);
    }

    public int updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    public boolean updateHrRoles(Integer hrId, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrId);
        return hrRoleMapper.addRole(hrId, rids) == rids.length;
    }

    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    public List<Hr> getAllHrsExceptCurrentHr() {
        return hrMapper.getAllHrsExceptCurrentHr(HrUtils.getCurrentHr().getId());
    }
}
