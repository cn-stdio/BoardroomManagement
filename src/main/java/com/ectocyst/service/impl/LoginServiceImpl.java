package com.ectocyst.service.impl;

import com.ectocyst.mapper.DoorMapper;
import com.ectocyst.mapper.EmployeeMapper;
import com.ectocyst.mapper.LoginMapper;
import com.ectocyst.model.Door;
import com.ectocyst.model.Employee;
import com.ectocyst.service.LoginService;
import com.ectocyst.utils.AliyunClientUtil;
import com.ectocyst.utils.Base64ToImageUtil;
import com.ectocyst.utils.FaceDetectionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/3/24 13:54
 * Description: 登录接口实现类
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private DoorMapper doorMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public JSONObject jobIdRegisterCheck(long jobId) {
        JSONObject jrc = new JSONObject();

        jrc.put("code", 200);
        int count = loginMapper.queryEmployeeRegister(jobId);
        if(count == 0) {
            jrc.put("msg", "exist");
        } else {
            jrc.put("msg", "never");
        }

        return jrc;
    }

    @Override
    public JSONObject loginCheck(long jobId, String password) {
        JSONObject lc = new JSONObject();
        lc.put("code", 200);

        Employee employee = employeeMapper.queryEmployeeByJobId(jobId);
        if(employee==null) {
            lc.put("msg", "查无此人！");

            return lc;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Boolean pw = bCryptPasswordEncoder.matches(password, employee.getPassword());

        if(pw==true) {
            lc.put("msg", "success");
        } else if(pw==false) {
            lc.put("msg", "密码错误！");
        } else {
            lc.put("msg", "error！");
        }

        return lc;
    }

    @Override
    public void faceDetection(String base) {
        File file = Base64ToImageUtil.Base64ToImage(base, "D:\\picture\\face.jpg");
        List<Employee> employees = employeeMapper.queryAllEmployee();
        FaceDetectionUtil faceDetectionUtil = new FaceDetectionUtil();

        /* 迭代删除所有未认证的人员 */
        Iterator<Employee> it = employees.iterator();
        while(it.hasNext()){
            Employee em = it.next();
            if(em.getImgUrl()==null){
                it.remove();
            }
        }

        employees.forEach(employee -> {
            AliyunClientUtil.downloadFile("ectocyst", "employee/" + employee.getJobId() + ".jpg");
            File f1 = new File("D:\\picture\\faceJudge.jpg");
            if(faceDetectionUtil.faceEngine(file, f1) >= 0.7) {
                System.out.println("相似");
                Door door = doorMapper.queryDoor();
                if(door.getSta()==0 && door.getFlag()==0) {
                    door.setSta(1);
                } else if ( door.getSta()==1 && door.getFlag()==1 ) {
                    door.setSta(0);
                }
            }
        });

    }





}
