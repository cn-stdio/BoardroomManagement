package com.ectocyst.controller;

import com.ectocyst.model.Boardroom;
import com.ectocyst.model.Employee;
import com.ectocyst.service.MyCenterService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;

/**
 * @author Seagull_gby
 * @date 2019/3/25 10:13
 * Description: 个人中心跳转
 */

@Controller
public class MyCenterController {

    @Autowired
    private MyCenterService myCenterService;

    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @RequestMapping("/privateCenter")
    public JSONObject privateCenter(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        JSONObject pc = new JSONObject();
        long jobId;

        if(principal == null) {
            if(request.getParameter("jobId")==null) {
                pc.put("code", 500);
                pc.put("msg", "请求失败，请先登录！");
                return pc;
            } else {
                jobId = Long.valueOf(request.getParameter("jobId"));
            }
        } else {
            jobId = Long.valueOf(principal.getName());
        }

        pc = myCenterService.privateCenterOfEmployee(jobId);

        return pc;
    }

    /**
     * 得到全部人员信息
     * @return JSON
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/getEmployeeMsg")
    public JSONObject getEmployeeMsg() {
        JSONObject gem = new JSONObject();

        gem = myCenterService.getAllEmployeeMsg();

        return gem;
    }

    /**
     * 更新指定人员信息
     * @param request 请求域
     * @return JSON
     * @throws Exception
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/updateEmployeeMsg")
    public JSONObject updateEmployeeMsg(HttpServletRequest request) throws Exception {
        JSONObject uem = new JSONObject();
        Employee employee = new Employee();

        employee.setJobId(Long.valueOf(request.getParameter("jobId")));
        employee.setName(request.getParameter("name"));
        employee.setSex(Integer.valueOf(request.getParameter("sex")));
        employee.setPosition(request.getParameter("position"));
        employee.setDepartment(request.getParameter("department"));
        employee.setImgUrl(request.getParameter("img"));
        employee.setPhone(request.getParameter("phone"));


        uem = myCenterService.updateEmployeeMsg(employee);

        return uem;
    }

    /**
     * 添加人员基础待注册信息
     * @param request 请求域
     * @return JSON
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/insertEmployeeMsg")
    public JSONObject insertEmployeeBasicMsg(HttpServletRequest request) {
        JSONObject iebm = new JSONObject();

        long jobId = Long.valueOf(request.getParameter("jobId"));
        String name = request.getParameter("name");

        iebm = myCenterService.insertEmployeeBasicMsg(jobId, name);

        return iebm;
    }

    /**
     * 删除指定人员
     * @param request 请求域
     * @return JSON
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/deleteEmployee")
    public JSONObject deleteEmployeeById(HttpServletRequest request) {
        JSONObject debi = new JSONObject();

        long jobId = Long.valueOf(request.getParameter("jobId"));

        debi = myCenterService.deleteEmployeeById(jobId);

        return debi;
    }

    /**
     * 得到全部会议室信息
     * @return JSON
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/getAllBoardroomMsg")
    public JSONObject getAllBoardroomMsg() {
        JSONObject gabm = new JSONObject();

        gabm = myCenterService.getAllBoardroomMsg();

        return gabm;
    }

    /**
     * 更新指定会议室信息
     * @param request 请求域
     * @return JSON
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/updateBoardroomMsg")
    public JSONObject updateBoardroomMsg(HttpServletRequest request) {
        JSONObject ubm = new JSONObject();
        Boardroom boardroom = new Boardroom();

        boardroom.setBoardroomId(Long.valueOf(request.getParameter("boardroomId")));
        boardroom.setBoardroomName(request.getParameter("boardroomName"));
        boardroom.setBoardroomImg(request.getParameter("boardroomImg"));
        boardroom.setCapacity(request.getParameter("capacity"));

        ubm = myCenterService.updateBoardroomMsg(boardroom);

        return ubm;
    }

    /**
     * 添加一条会议室记录
     * @param request 请求域
     * @return JSON
     * @throws Exception
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/insertBoardroom")
    public JSONObject insertBoardroomMsg(HttpServletRequest request) throws Exception {
        JSONObject ibm = new JSONObject();
        Boardroom boardroom = new Boardroom();

        boardroom.setBoardroomId(Long.valueOf(request.getParameter("boardroomId")));
        boardroom.setBoardroomName(request.getParameter("boardroomName"));
        boardroom.setBoardroomImg(request.getParameter("boardroomImg"));
        boardroom.setCapacity(request.getParameter("capacity"));

        ibm = myCenterService.insertBoardroom(boardroom);

        return ibm;
    }

    /**
     * 删除指定会议室
     * @param request 请求域
     * @return JSON
     */
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/deleteBoardroom")
    public JSONObject deleteBoardroomMsg(HttpServletRequest request) {
        JSONObject dbm = new JSONObject();

        long boardroomId = Long.valueOf(request.getParameter("boardroomId"));

        dbm = myCenterService.deleteBoardroom(boardroomId);

        return dbm;
    }

    /**
     * 删除所有预约信息（包括参与者）
     * @param request 请求域
     * @return JSON
     * @throws ParseException
     */
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/deleteReservationById")
    public JSONObject deleteReservationById(HttpServletRequest request) throws ParseException {
        JSONObject drbi = new JSONObject();

        long boardroomId = Long.valueOf(request.getParameter("boardroomId"));
        String date = request.getParameter("date");

        drbi = myCenterService.deleteBoardroomReservation(boardroomId, date);

        return drbi;
    }

    /**
     * 删除预约信息跳转页
     * @param principal 获取用户名
     * @return JSON
     * @throws ParseException
     */
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @RequestMapping("/deleteReservation")
    public JSONObject deleteReservation(@AuthenticationPrincipal Principal principal, HttpServletRequest request) throws ParseException {
        JSONObject dr = new JSONObject();

        long jobId;

        if(principal == null) {
            if(request.getParameter("jobId")==null) {
                dr.put("code", 500);
                dr.put("msg", "请求失败，请先登录！");
                return dr;
            } else {
                jobId = Long.valueOf(request.getParameter("jobId"));
            }
        } else {
            jobId = Long.valueOf(principal.getName());
        }

        dr = myCenterService.getBoardroomReservationOfDelete(jobId);

        return dr;
    }

    /**
     * 获得所有预约与参与会议
     * @param principal 获取用户名
     * @return JSON
     * @throws ParseException
     */
    /*@PreAuthorize("hasRole('USER')")*/
    @ResponseBody
    @RequestMapping("/getMyMeeting")
    public JSONObject myMeeting(@AuthenticationPrincipal Principal principal, HttpServletRequest request) throws ParseException {
        JSONObject mm = new JSONObject();

        long jobId;

        if(principal == null) {
            if(request.getParameter("jobId")==null) {
                mm.put("code", 500);
                mm.put("msg", "请求失败，请先登录！");
                return mm;
            } else {
                jobId = Long.valueOf(request.getParameter("jobId"));
            }
        } else {
            jobId = Long.valueOf(principal.getName());
        }

        mm = myCenterService.getMyMeeting(jobId);

        return mm;
    }

    /**
     * 获取指定预约会议室的参与人员（不包括预约者）
     * @param principal 获取用户名
     * @param request 请求域
     * @return JSON
     * @throws ParseException
     */
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @RequestMapping("/getMeetingEmployee")
    public JSONObject meetingEmployee(@AuthenticationPrincipal Principal principal, HttpServletRequest request) throws ParseException {
        JSONObject me = new JSONObject();

        long jobId;

        if(principal == null) {
            if(request.getParameter("jobId")==null) {
                me.put("code", 500);
                me.put("msg", "请求失败，请先登录！");
                return me;
            } else {
                jobId = Long.valueOf(request.getParameter("jobId"));
            }
        } else {
            jobId = Long.valueOf(principal.getName());
        }

        long boardroomId = Long.valueOf(request.getParameter("boardroomId"));
        String time = request.getParameter("time");

        me = myCenterService.getMeetingEmployee(boardroomId, time, jobId);

        return me;
    }

    /**
     * 继续添加会议室人员
     * @param request 请求域
     * @return JSON
     * @throws ParseException
     */
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @RequestMapping("/insertMeetingEmployee")
    public JSONObject plusMeetingEmployee(HttpServletRequest request) throws ParseException {
        JSONObject pme = new JSONObject();
        long jobId = Long.valueOf(request.getParameter("jobId"));
        long boardroomId = Long.valueOf(request.getParameter("boardroomId"));
        String time = request.getParameter("time");

        pme = myCenterService.insertMeetingEmployee(jobId, boardroomId, time);

        return pme;
    }

    /**
     * 继续删除会议室人员
     * @param request 请求域
     * @return JSON
     * @throws ParseException
     */
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @RequestMapping("/deleteMeetingEmployee")
    public JSONObject reduceMeetingEmployee(HttpServletRequest request) throws ParseException {
        JSONObject rme = new JSONObject();

        long jobId = Long.valueOf(request.getParameter("jobId"));
        long boardroomId = Long.valueOf(request.getParameter("boardroomId"));
        String time = request.getParameter("time");

        rme = myCenterService.deleteMeetingEmployee(jobId, boardroomId, time);

        return rme;
    }
}
