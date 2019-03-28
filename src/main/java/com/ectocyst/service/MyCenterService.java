package com.ectocyst.service;

import com.ectocyst.model.Boardroom;
import com.ectocyst.model.Employee;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * @author Seagull_gby
 * @date 2019/3/25 10:02
 * Description: 个人中心接口
 */

@Service
public interface MyCenterService {

    /**
     * 获得所有人员信息
     * @return JSON
     */
    public JSONObject getAllEmployeeMsg();

    /**
     * 更新指定人员信息
     * @param employee 人员
     * @return JSON
     */
    public JSONObject updateEmployeeMsg(Employee employee) throws Exception;

    /**
     * 添加人员基础待注册信息
     * @param jobId 工号
     * @param name 名称
     * @return JSON
     */
    public JSONObject insertEmployeeBasicMsg(long jobId, String name);

    /**
     * 删除指定人员
     * @param jobId 工号
     * @return JSON
     */
    public JSONObject deleteEmployeeById(long jobId);

    /**
     * 获得所有会议室信息
     * @return JSON
     */
    public JSONObject getAllBoardroomMsg();

    /**
     * 修改指定会议室信息
     * @param boardroom 指定会议室
     * @return JSON
     */
    public JSONObject updateBoardroomMsg(Boardroom boardroom);

    /**
     * 添加会议室
     * @param boardroom 会议室
     * @return JSON
     */
    public JSONObject insertBoardroom(Boardroom boardroom) throws Exception;

    /**
     * 删除指定会议室
     * @param boardroomId 会议室ID
     * @return JSON
     */
    public JSONObject deleteBoardroom(long boardroomId);

    /**
     * 删除会议室预约信息
     * @param boardroomId 会议室ID
     * @param date 时间
     * @return JSON
     * @throws ParseException
     */
    public JSONObject deleteBoardroomReservation(long boardroomId, String date) throws ParseException;

    /**
     * 获得取消页面的所有会议室预订信息（自身）
     * @return
     * @throws ParseException
     */
    public JSONObject getBoardroomReservationOfDelete(long jobId) throws ParseException;

    /**
     * 个人中心跳转获取登录信息
     * @param jobId 工号
     * @return JSON
     */
    public JSONObject privateCenterOfEmployee(long jobId);

    /**
     * 获取所有预约与参与的会议
     * @param jobId 工号
     * @return JSON
     * @throws ParseException
     */
    public JSONObject getMyMeeting(long jobId) throws ParseException;

    /**
     * 获得指定预约会议室的参与人员信息
     * @param boardroomId 会议室编号
     * @param time 起始时间（格式：2019-02-02 02:02）
     * @param jobId 预约者工号
     * @return JSON
     * @throws ParseException
     */
    public JSONObject getMeetingEmployee(long boardroomId, String time, long jobId) throws ParseException;

    /**
     * 在预约的基础上继续添加成员
     * @param jobId 成员工号
     * @param boardroomId 预约的会议室ID
     * @param time 起始时间（格式：2019-02-02 02:02）
     * @return JSON
     * @throws ParseException
     */
    public JSONObject insertMeetingEmployee(long jobId, long boardroomId, String time) throws ParseException;

    /**
     * 在预约的基础上继续删除成员
     * @param jobId 成员工号
     * @param boardroomId 预约的会议室ID
     * @param time 起始时间（格式：2019-02-02 02:02）
     * @return JSON
     * @throws ParseException
     */
    public JSONObject deleteMeetingEmployee(long jobId, long boardroomId, String time) throws ParseException;
}
