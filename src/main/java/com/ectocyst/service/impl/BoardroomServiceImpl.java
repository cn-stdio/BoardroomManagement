package com.ectocyst.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.ectocyst.component.StartTimeDeal;
import com.ectocyst.component.TestReservation;
import com.ectocyst.mapper.BoardroomMapper;
import com.ectocyst.mapper.EmployeeMapper;
import com.ectocyst.mapper.ReservationMapper;
import com.ectocyst.model.Boardroom;
import com.ectocyst.model.BoardroomPartakeEmployee;
import com.ectocyst.model.BoardroomReservation;
import com.ectocyst.model.Employee;
import com.ectocyst.service.BoardroomService;
import com.ectocyst.utils.AliyunClientUtil;
import com.ectocyst.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/2/28 19:16
 * Description: 会议室操作实现类
 */
@Service
public class BoardroomServiceImpl implements BoardroomService {

    @Autowired
    private BoardroomMapper boardroomMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private TestReservation testReservation;

    @Autowired
    private StartTimeDeal startTimeDeal;

    public static final int END_TIME = 23;
    public static final int START_TIME = 0;
    public static final String YEAR = "2019";

    /**
     * 查询所有会议室记录
     * @return 制定JSON
     */
    @Override
    public JSONObject queryAllBoardroom() {

        JSONObject returnBoardroom = new JSONObject();
        TimeUtil timeUtil = new TimeUtil();

        returnBoardroom.put("code", 200);
        returnBoardroom.put("msg", "success");

        JSONArray data = new JSONArray();
        List<Boardroom> boardrooms = new ArrayList<>();
        boardrooms = boardroomMapper.queryBoardroom();

        boardrooms.forEach(boardroom -> {
            JSONObject oneOfBoardroom = new JSONObject();
            oneOfBoardroom.put("id", boardroom.getBoardroomId());
            oneOfBoardroom.put("title", boardroom.getBoardroomName());
            oneOfBoardroom.put("img", boardroom.getBoardroomImg());

            String currentDate = null;
            try {
                currentDate = timeUtil.getFormatDateOfmd(new Date());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String reservationJudge = testReservation.testReservationByBoardroomIdAndDate(boardroom.getBoardroomId(), currentDate);
            if (reservationJudge.equals("YES")) {
                oneOfBoardroom.put("useable", 1);
            } else if (reservationJudge.equals("NO")) {
                oneOfBoardroom.put("useable", 0);
            } else {
                oneOfBoardroom.put("useable", "error");
            }

            data.add(oneOfBoardroom);
        });

        returnBoardroom.put("data", data);

        return returnBoardroom;
    }

    /**
     * 查询当前日期所有会议室预约情况
     * @return 预约情况
     */
    @Override
    public JSONObject queryReservationOfCurrentDate() {

        JSONObject returnReservation = new JSONObject();

        returnReservation.put("code", 200);
        returnReservation.put("msg", "success");

        List<Boardroom> boardrooms = new ArrayList<>();
        boardrooms = boardroomMapper.queryBoardroom();

        JSONArray data = new JSONArray();
        JSONArray rooms = new JSONArray();

        boardrooms.forEach(boardroom -> {
            List<Date> startTimes = new ArrayList<>();

            startTimes = testReservation.getReservationTimeOfCurrentDateByBoardroomId(boardroom.getBoardroomId());

            startTimes.forEach(startTime -> {
                JSONArray oneOfReservation = new JSONArray();
                oneOfReservation.add(boardroom.getBoardroomId());
                try {
                    oneOfReservation.add(Integer.parseInt(startTimeDeal.timeToHour(startTime)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                oneOfReservation.add(employeeMapper.queryCountByPartakeTime(startTime, boardroom.getBoardroomId()));

                data.add(oneOfReservation);
            });

            rooms.add(boardroom.getBoardroomName());
        });

        returnReservation.put("data", data);
        returnReservation.put("rooms", rooms);

        return returnReservation;
    }

    /**
     * 查询今天目标会议室预约情况
     * @param boardroomId 会议室Id
     * @return JSON
     * @throws ParseException
     */
    @Override
    public JSONObject queryReservationByToday(long boardroomId) throws ParseException {
        JSONObject returnReservation = new JSONObject();
        JSONObject data = new JSONObject();
        TimeUtil timeUtil = new TimeUtil();

        String boardroomImg = boardroomMapper.queryBoardroomImgById(boardroomId);

        if(boardroomImg == null) {
            returnReservation.put("code", 500);
            returnReservation.put("msg", "未找到目标会议室");

            return returnReservation;
        }

        data.put("img", boardroomImg);
        JSONObject listData = new JSONObject();
        JSONArray tableData = new JSONArray();
        JSONObject people = new JSONObject();

        Date nowTime = new Date();
        int flag = reservationMapper.queryCountByBoardroomIdAndTime(boardroomId, nowTime);

        String boardroomName = boardroomMapper.queryBoardroomNameById(boardroomId);
        String startTime = timeUtil.getFormatDateOfHHmm(nowTime);
        startTime = startTime.substring(0, startTime.length()-2);
        startTime += "00";

        /* 查询是否正在使用并将使用人员加入JSON */
        if(flag == 0) {
            data.put("useable", 0);
        } else if (flag == 1) {
            data.put("useable", 1);
            BoardroomReservation boardroomReservation = new BoardroomReservation();
            boardroomReservation = reservationMapper.queryReservationByBoardroomIdAndTime(boardroomId, nowTime);

            String name = employeeMapper.queryEmployeeNameByJobId(boardroomReservation.getReservationId());
            Date startDate = boardroomReservation.getStartTime();
            List<BoardroomPartakeEmployee> boardroomPartakeEmployees = employeeMapper.queryReservationByPartakeTime(startDate, boardroomId);
            int num = boardroomPartakeEmployees.size();
            JSONArray partPeople = new JSONArray();
            boardroomPartakeEmployees.forEach(boardroomPartakeEmployee -> {
                String nameTemporary = employeeMapper.queryEmployeeNameByJobId(boardroomPartakeEmployee.getJobId());
                if(!nameTemporary.equals(name)) {
                    partPeople.add(nameTemporary);
                }
            });
            String times = startTime + "-" + timeUtil.getFormatDateOfHHmm(boardroomReservation.getEndTime());

            people.put("name", name);
            people.put("num", num);
            people.put("people", partPeople);
            people.put("time", times);
        }
        tableData.add(people);
        listData.put("tableData", tableData);

        /* 将当前时间之后直到END_TIME的时间加入JSON */
        JSONArray roomData = new JSONArray();
        String startTimeFront = startTime.substring(0, startTime.length()-3);
        int tt = Integer.parseInt(startTimeFront);
        if(flag == 1) {
            tt ++;
        }
        while(tt!=END_TIME) {
            String tm = null;
            JSONObject tj = new JSONObject();

            tm = startTimeDeal.hourIntToTime(tt);
            tt ++;

            tj.put("date", tm);
            tj.put("name", boardroomName);

            roomData.add(tj);
        }
        listData.put("roomData", roomData);

        data.put("listData", listData);

        returnReservation.put("code", 200);
        returnReservation.put("msg", "success");
        returnReservation.put("data", data);

        return returnReservation;
    }

    @Override
    public JSONObject queryFreeDateOfCurrentDate() throws ParseException {
        JSONObject freeRoom = new JSONObject();
        TimeUtil timeUtil = new TimeUtil();
        Date nowDate = new Date();

        JSONObject data = new JSONObject();
        JSONArray roomData = new JSONArray();

        /* 对会议室遍历获取每一个会议室的空闲时间 */
        List<Boardroom> boardrooms = boardroomMapper.queryBoardroom();
        for (Boardroom boardroom : boardrooms){
            List<Date> dates = reservationMapper.queryReservationStartTimeByBoardroomIdAndDate(boardroom.getBoardroomId(), timeUtil.getFormatDateOfmd(nowDate));
            List<String> freeDateString = startTimeDeal.getFreeTimeOfDate(dates, timeUtil.getFormatDateOfmd(nowDate));

            JSONObject rd = new JSONObject();
            freeDateString.forEach(f -> {
                rd.put("date", f);
                rd.put("name", boardroom.getBoardroomName());

                roomData.add(rd);
            });
        }
        data.put("roomData", roomData);

        freeRoom.put("code", 200);
        freeRoom.put("msg", "success");
        freeRoom.put("data", data);
        return freeRoom;
    }

    @Override
    public JSONObject queryReservationOfSevenDay(String date, long boardroomId, String time) throws ParseException {
        JSONObject ros = new JSONObject();

        JSONObject data = new JSONObject();
        JSONArray list = new JSONArray();

        List<Date> dates = new ArrayList<>();
        dates = reservationMapper.queryReservationStartTimeByBoardroomIdAndDate(boardroomId, date);

        Boardroom boardroom = boardroomMapper.queryBoardroomById(boardroomId);

        List<String> freeDateString = startTimeDeal.getFreeTimeOfDate(dates, date);
        JSONObject rosChild = new JSONObject();

        Date nowDate = new Date();
        TimeUtil timeUtil = new TimeUtil();
        int flag=0, sTI=0, eTI=0;
        if (time.equals("all")) {
            flag = 1;
        } else {
            String startTime = time.substring(0, time.length() - 9);
            String endTime = time.substring(6, time.length() - 3);
            sTI = Integer.parseInt(startTime);
            eTI = Integer.parseInt(endTime);

            if (timeUtil.getFormatDateOfmd(nowDate).equals(date)) {
                String nowH = timeUtil.getFormatDateOfHH(nowDate);
                int nowHI = Integer.parseInt(nowH);

                if (nowHI < sTI) {
                    flag = sTI;
                } else {
                    flag = nowHI;
                }

            } else {
                flag = sTI;
            }
        }
        for (String fds : freeDateString) {
            JSONObject rc = new JSONObject();
            int fdsI = Integer.parseInt(fds.substring(0, fds.length() - 9));

            if ( (fdsI >= flag && fdsI < eTI) || flag == 1 ) {
                rc.put("id", boardroomId);
                rc.put("title", boardroom.getBoardroomName());
                rc.put("img", boardroom.getBoardroomImg());
                rc.put("time", fds);

                list.add(rc);
            }
        }

        data.put("list", list);

        ros.put("code", 200);
        ros.put("msg", "success");
        ros.put("data", data);
        return ros;
    }

    @Override
    public JSONObject conferenceRoomReservation() {
        JSONObject crr = new JSONObject();
        JSONArray data = new JSONArray();

        List<Boardroom> boardrooms = boardroomMapper.queryBoardroom();
        boardrooms.forEach(boardroom -> {
            JSONObject dataUnit = new JSONObject();

            dataUnit.put("boardroomId", boardroom.getBoardroomId());
            dataUnit.put("boardroomName", boardroom.getBoardroomName());

            data.add(dataUnit);
        });

        crr.put("code", 200);
        crr.put("msg", "success");
        crr.put("data", data);
        return crr;
    }

    @Override
    public JSONObject insertReservationBoardroom(long jobId, long boardroomId, String peopleList, String date, String time) throws ParseException, ClientException {
        JSONObject irb = new JSONObject();
        TimeUtil timeUtil = new TimeUtil();

        /* 截取为 8:00 格式 */
        String startTime = time.substring(0, time.length() - 6);
        String sdateDeal = YEAR + "-" + date.substring(0, 2) + "-" + date.substring(3, 5) + " " + startTime;
        Date startDate = timeUtil.getFormatStringOfH(sdateDeal);

        String endTime = time.substring(6, time.length());
        String edateDeal = YEAR + "-" + date.substring(0, 2) + "-" + date.substring(3, 5) + " " + endTime;
        Date endDate = timeUtil.getFormatStringOfH(edateDeal);

        BoardroomReservation boardroomReservation = new BoardroomReservation();
        boardroomReservation.setBoardroomId(boardroomId);
        boardroomReservation.setDate(date);
        boardroomReservation.setReservationId(jobId);
        boardroomReservation.setStartTime(startDate);
        boardroomReservation.setEndTime(endDate);

        reservationMapper.insertReservationBoardroom(boardroomReservation);

        /* 插入人员信息 */
        String[] labels = peopleList.split(",");
        for (String lb : labels) {

            BoardroomPartakeEmployee boardroomPartakeEmployee = new BoardroomPartakeEmployee();
            boardroomPartakeEmployee.setJobId(Integer.valueOf(lb));
            boardroomPartakeEmployee.setPartakeConference((int) boardroomId);
            boardroomPartakeEmployee.setPartakeTime(startDate);

            reservationMapper.insertReservationEmployee(boardroomPartakeEmployee);

            /* 对每个人员发送手机验证码通知(未上线无法申请) */
/*
            Employee e = employeeMapper.queryEmployeeByJobId(Long.valueOf(lb));
            AliyunClientUtil.sendSms(e.getPhone(), e.getName(), employeeMapper.queryEmployeeNameByJobId(jobId), sdateDeal, boardroomMapper.queryBoardroomNameById(boardroomId));
*/
        }

        irb.put("code", 200);
        irb.put("msg", "success");
        return irb;
    }
}
