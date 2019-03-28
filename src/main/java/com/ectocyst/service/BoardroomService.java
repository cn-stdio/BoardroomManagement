package com.ectocyst.service;

import com.aliyuncs.exceptions.ClientException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * @author Seagull_gby
 * @date 2019/2/28 19:15
 * Description: 会议室操作接口
 */
@Service
public interface BoardroomService {

    /**
     * 查询所有会议室记录
     * @return 指定JSON
     */
    public JSONObject queryAllBoardroom();

    /**
     * 查询当前日期所有会议室预约情况
     * @return 预约情况
     */
    public JSONObject queryReservationOfCurrentDate();

    /**
     * 查询今天目标会议室预约情况
     * @param boardroomId 会议室Id
     * @return JSON
     * @throws ParseException
     */
    public JSONObject queryReservationByToday(long boardroomId) throws ParseException;

    /**
     * 查询当天空闲会议室
     * @return JSON
     * @throws ParseException
     */
    public JSONObject queryFreeDateOfCurrentDate() throws ParseException;

    /**
     * 查询7天内的空闲预约情况
     * @param date 日期
     * @param boardroomId 会议室编号
     * @param time 时间段（非必须，若无则为"all"）
     * @return JSON
     */
    public JSONObject queryReservationOfSevenDay(String date, long boardroomId, String time) throws ParseException;

    /**
     * 查询会议室ID和名字（预订页面跳转）
     * @return JSON
     */
    public JSONObject conferenceRoomReservation();


    /**
     * 插入会议室预约信息
     * @param jobId 预约者工号
     * @param boardroomId 会议室编号
     * @param peopleList 参与人员
     * @param date 日期（如：03月01日）
     * @param time 时间段（如 08:00-09:00）
     * @return JSON
     * @throws ParseException
     */
    public JSONObject insertReservationBoardroom(long jobId, long boardroomId, String peopleList, String date, String time) throws ParseException, ClientException;
}
