package com.ectocyst.controller;

import com.ectocyst.model.Employee;
import com.ectocyst.service.RegisterService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Seagull_gby
 * @date 2019/3/24 14:53
 * Description: 注册跳转
 */

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @ResponseBody
    @RequestMapping("/registerSuccess")
    public JSONObject registerSuccess(HttpServletRequest request) throws Exception {
        JSONObject rs = new JSONObject();
        Employee employee = new Employee();
        int s;
        String sex = request.getParameter("sex");
        if (sex == "男") {
            s = 1;
        } else {
            s = 0;
        }

        employee.setJobId(Long.valueOf(request.getParameter("jobId")));
        employee.setSex(s);
        employee.setPosition(request.getParameter("position"));
        employee.setDepartment(request.getParameter("department"));
        employee.setPassword(request.getParameter("password"));
        employee.setImgUrl(request.getParameter("img"));
        employee.setPhone(request.getParameter("phone"));

        rs = registerService.registerEmployee(employee);

        return rs;
    }
}
