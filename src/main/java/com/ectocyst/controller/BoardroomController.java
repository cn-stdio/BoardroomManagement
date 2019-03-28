package com.ectocyst.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ectocyst.service.BoardroomService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;

/**
 * @author Seagull_gby
 * @date 2019/3/1 14:40
 * Description: 会议室操作跳转
 */

@RestController
public class BoardroomController {

    @Autowired
    private BoardroomService boardroomService;

    @RequestMapping("/getBoardroom")
    public JSONObject getBoardroom() {

        JSONObject returnBoardroom = new JSONObject();

        returnBoardroom = boardroomService.queryAllBoardroom();

        return returnBoardroom;
    }

    @RequestMapping("/getReservationOfCurrentDate")
    public JSONObject getReservationOfCurrentDate() {

        JSONObject returnReservation = new JSONObject();

        returnReservation = boardroomService.queryReservationOfCurrentDate();

        return returnReservation;
    }

    @RequestMapping("/sortFreeRoom")
    public JSONObject getFreeRoomOfCurrentDate() throws ParseException {
        JSONObject freeRoom = new JSONObject();

        freeRoom = boardroomService.queryFreeDateOfCurrentDate();

        return freeRoom;
    }

    @RequestMapping("/getReservationOfSevenDay")
    public JSONObject getReservationOfSevenDay(HttpServletRequest request) throws ParseException {
        JSONObject ros = new JSONObject();

        String date = request.getParameter("date");
        long boardroomId = Long.parseLong(request.getParameter("boardroomId"));
        String time = request.getParameter("time");

        ros = boardroomService.queryReservationOfSevenDay(date, boardroomId, time);

        return ros;
    }

    @RequestMapping("/conferenceRoomReservation")
    public JSONObject conferenceRoomReservation() {
        JSONObject crr = new JSONObject();

        crr = boardroomService.conferenceRoomReservation();

        return crr;
    }

    @RequestMapping("/reservationBoardroom")
    public JSONObject reservationBoardroom(@AuthenticationPrincipal Principal principal, HttpServletRequest request) throws ParseException, ClientException {
        JSONObject rb = new JSONObject();

        long jobId;

        if(principal == null) {
            if(request.getParameter("jobId")==null) {
                rb.put("code", 500);
                rb.put("msg", "请求失败，请先登录！");
                return rb;
            } else {
                jobId = Long.valueOf(request.getParameter("jobId"));
            }
        } else {
            jobId = Long.valueOf(principal.getName());
        }

        long boardroomId = Long.valueOf(request.getParameter("boardroomId"));
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String peopleList = request.getParameter("peopleList");

        rb = boardroomService.insertReservationBoardroom(jobId, boardroomId, peopleList, date, time);

        return rb;
    }

}
