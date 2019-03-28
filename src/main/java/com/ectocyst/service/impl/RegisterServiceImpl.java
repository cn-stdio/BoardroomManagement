package com.ectocyst.service.impl;

import com.ectocyst.mapper.EmployeeMapper;
import com.ectocyst.mapper.RegisterMapper;
import com.ectocyst.mapper.RoleMapper;
import com.ectocyst.model.Employee;
import com.ectocyst.service.RegisterService;
import com.ectocyst.utils.AliyunClientUtil;
import com.ectocyst.utils.Base64ToImageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Seagull_gby
 * @date 2019/3/24 14:10
 * Description: 注册接口实现
 */

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public JSONObject registerEmployee(Employee employee) throws Exception {
        JSONObject re = new JSONObject();

        /* 密码加密 */
        String password = employee.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        password = encoder.encode(password);
        employee.setPassword(password);

        /* base64转图片 */
        File file = Base64ToImageUtil.Base64ToImage(employee.getImgUrl(), "D:\\picture\\" + employee.getJobId() + ".jpg");
        AliyunClientUtil.uploadObjectOSS(file, "ectocyst", "employee/");
        String imgUrl = AliyunClientUtil.getUrl("ectocyst", "employee", employee.getJobId() + ".jpg");
        employee.setImgUrl(imgUrl);

        int count = registerMapper.insertEmployee(employee);
        int countRole = roleMapper.insertRoleOfEmployee(employee.getJobId(), 2);

        if(count == 0) {
            re.put("code", 500);
            re.put("msg", "注册失败");
            return re;
        }

        if(countRole == 0) {
            re.put("code", 500);
            re.put("msg", "权限注册失败");
            employeeMapper.deleteEmployById(employee.getJobId());
            return re;
        }


        re.put("code", 200);
        re.put("msg", "success");
        return re;
    }
}
