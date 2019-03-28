package com.ectocyst.service.security;

import com.ectocyst.mapper.EmployeeMapper;
import com.ectocyst.model.Employee;
import com.ectocyst.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/3/23 12:51
 * Description: 权限认证将用户名与权限关系储存
 */
@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户

        Employee user = employeeMapper.queryEmployByJobIdAndRole(Long.valueOf(username));

        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。

        String roleName = employeeMapper.queryUserRole(employeeMapper.queryRoleId(user.getJobId()));
        authorities.add(new SimpleGrantedAuthority(roleName));

        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getJobId()),
                user.getPassword(), authorities);
    }
}

