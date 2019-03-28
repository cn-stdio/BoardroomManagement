package com.ectocyst.controller;

import com.ectocyst.service.BoardroomService;
import com.ectocyst.service.EmployeeService;
import com.ectocyst.utils.Base64ToImageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * @author Seagull_gby
 * @date 2019/3/7 19:59
 * Description: 用户连接跳转
 */

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BoardroomService boardroomService;

    @ResponseBody
    @RequestMapping("/getReservationEmployee")
    public JSONObject getReservationEmployee(HttpServletRequest request) {
        JSONObject returnReservationEmployee = new JSONObject();

        long jobId = Long.parseLong(request.getParameter("jobId"));
        returnReservationEmployee = employeeService.getReservationEmployee(jobId);

        return returnReservationEmployee;
    }

    @ResponseBody
    @RequestMapping("/getCurrentDateReservation")
    public JSONObject getCurrentDateReservation(HttpServletRequest request) throws ParseException {
        JSONObject returnCurrentDateReservation = new JSONObject();

        long boardroomId = Long.parseLong(request.getParameter("boardroomId"));
        returnCurrentDateReservation = boardroomService.queryReservationByToday(boardroomId);

        return  returnCurrentDateReservation;
    }

    @GetMapping("/aaa")
    public String test() {
        return "aaa";
    }

    @RequestMapping("/abb")
    public JSONObject t(String src) throws FileNotFoundException {
        JSONObject jj = new JSONObject();
        jj.put("src", src);
        String path = "D:\\";


        return jj;
    }
}
