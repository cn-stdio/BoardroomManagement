package com.ectocyst.service.impl;

import com.ectocyst.component.StartTimeDeal;
import com.ectocyst.mapper.BoardroomMapper;
import com.ectocyst.mapper.EmployeeMapper;
import com.ectocyst.mapper.MyCenterMapper;
import com.ectocyst.mapper.ReservationMapper;
import com.ectocyst.model.Boardroom;
import com.ectocyst.model.BoardroomPartakeEmployee;
import com.ectocyst.model.BoardroomReservation;
import com.ectocyst.model.Employee;
import com.ectocyst.service.MyCenterService;
import com.ectocyst.utils.AliyunClientUtil;
import com.ectocyst.utils.Base64ToImageUtil;
import com.ectocyst.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/3/25 10:05
 * Description: 个人中心接口实现
 */

@Service
public class MyCenterServiceImpl implements MyCenterService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private BoardroomMapper boardroomMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private MyCenterMapper myCenterMapper;

    @Autowired
    private StartTimeDeal startTimeDeal;

    @Override
    public JSONObject getAllEmployeeMsg() {
        JSONObject gaem = new JSONObject();
        JSONArray data = new JSONArray();

        List<Employee> employees = employeeMapper.queryAllEmployee();
        employees.forEach(employee -> {
            JSONObject dataUnit = new JSONObject();

            if(employee.getImgUrl()==null) {
                dataUnit.put("jobId", employee.getJobId());
                dataUnit.put("name", employee.getName());
                dataUnit.put("tag", "否");
            } else {
                dataUnit.put("jobId", employee.getJobId());
                dataUnit.put("name", employee.getName());

                if (employee.getSex() == 1) {
                    dataUnit.put("sex", "男");
                } else {
                    dataUnit.put("sex", "女");
                }

                dataUnit.put("position", employee.getPosition());
                dataUnit.put("department", employee.getDepartment());
                dataUnit.put("phone", employee.getPhone());
                dataUnit.put("imgUrl", employee.getImgUrl());
                dataUnit.put("tag", "是");
            }

            data.add(dataUnit);
        });

        gaem.put("code", 200);
        gaem.put("msg", "success");
        gaem.put("data", data);
        return gaem;
    }

    @Override
    public JSONObject updateEmployeeMsg(Employee employee) throws Exception {
        JSONObject uem = new JSONObject();

        /* base64转图片 */
        File file = Base64ToImageUtil.Base64ToImage(employee.getImgUrl(), "D:\\picture\\" + employee.getJobId() + ".jpg");
        AliyunClientUtil.uploadObjectOSS(file, "ectocyst", "employee/");
        String imgUrl = AliyunClientUtil.getUrl("ectocyst", "employee", employee.getJobId() + ".jpg");
        employee.setImgUrl(imgUrl);

        int count = myCenterMapper.updateEmployee(employee);

        if(count == 0) {
            uem.put("code", 500);
            uem.put("msg", "更新失败");
            return uem;
        }

        uem.put("code", 200);
        uem.put("msg", "success");
        return uem;
    }

    @Override
    public JSONObject insertEmployeeBasicMsg(long jobId, String name) {
        JSONObject iebm = new JSONObject();

        int count = myCenterMapper.insertBasicEmployee(jobId, name);
        if(count == 0) {
            iebm.put("code", 500);
            iebm.put("msg", "插入失败");
            return iebm;
        }

        iebm.put("code", 200);
        iebm.put("msg", "success");
        return iebm;
    }

    @Override
    public JSONObject deleteEmployeeById(long jobId) {
        JSONObject debi = new JSONObject();

        int count = myCenterMapper.deleteEmployeeById(jobId);
        int countDl = myCenterMapper.deleteEmployeeRoleById(jobId);
        if(count == 0 || countDl == 0) {
            debi.put("code", 500);
            debi.put("msg", "删除失败");
            return debi;
        }

        debi.put("code", 200);
        debi.put("msg", "success");
        return debi;
    }

    @Override
    public JSONObject getAllBoardroomMsg() {
        JSONObject gabm = new JSONObject();

        List<Boardroom> boardrooms = new ArrayList<>();
        boardrooms = boardroomMapper.queryBoardroom();

        JSONArray data = new JSONArray();
        boardrooms.forEach(boardroom -> {
            JSONObject dataUnit = new JSONObject();

            dataUnit.put("boardroomId", boardroom.getBoardroomId());
            dataUnit.put("boardroomName", boardroom.getBoardroomName());
            dataUnit.put("boardroomImg", boardroom.getBoardroomImg());
            dataUnit.put("capacity", boardroom.getCapacity());

            data.add(dataUnit);
        });

        gabm.put("code", 200);
        gabm.put("msg", "success");
        gabm.put("data", data);
        return gabm;
    }

    @Override
    public JSONObject updateBoardroomMsg(Boardroom boardroom) {
        JSONObject ubm = new JSONObject();

        /* base64转图片 */
        File file = Base64ToImageUtil.Base64ToImage(boardroom.getBoardroomImg(), "D:\\picture\\" + boardroom.getBoardroomId() + ".jpg");
        AliyunClientUtil.uploadObjectOSS(file, "ectocyst", "boardroom/");
        String imgUrl = AliyunClientUtil.getUrl("ectocyst", "boardroom", boardroom.getBoardroomId() + ".jpg");
        boardroom.setBoardroomImg(imgUrl);

        int count = myCenterMapper.updateBoardroomById(boardroom);
        if (count == 0) {
            ubm.put("code", 500);
            ubm.put("msg", "修改失败！");

            return ubm;
        }

        ubm.put("code", 200);
        ubm.put("msg", "success");
        return ubm;
    }

    @Override
    public JSONObject insertBoardroom(Boardroom boardroom) throws Exception {
        JSONObject ib = new JSONObject();

        /* base64转图片 */
        File file = Base64ToImageUtil.Base64ToImage(boardroom.getBoardroomImg(), "D:\\picture\\" + boardroom.getBoardroomId() + ".jpg");
        AliyunClientUtil.uploadObjectOSS(file, "ectocyst", "boardroom/");
        String imgUrl = AliyunClientUtil.getUrl("ectocyst", "boardroom", boardroom.getBoardroomId() + ".jpg");
        boardroom.setBoardroomImg(imgUrl);

        int count = myCenterMapper.insertBoardroom(boardroom);
        if (count == 0) {
            ib.put("code", 500);
            ib.put("msg", "添加失败！");

            return ib;
        }

        ib.put("code", 200);
        ib.put("msg", "success");
        return ib;
    }

    @Override
    public JSONObject deleteBoardroom(long boardroomId) {
        JSONObject db = new JSONObject();

        int count = myCenterMapper.deleteBoardroomById(boardroomId);
        if (count == 0) {
            db.put("code", 500);
            db.put("msg", "删除失败！");

            return db;
        }

        db.put("code", 200);
        db.put("msg", "success");
        return db;
    }

    @Override
    public JSONObject deleteBoardroomReservation(long boardroomId, String date) throws ParseException {
        JSONObject dbr = new JSONObject();
        TimeUtil timeUtil = new TimeUtil();

        Date dd = timeUtil.getFormatStringOfH(date);
        myCenterMapper.deleteReservationBoardroom(dd, boardroomId);
        myCenterMapper.deleteReservationEmployeeOfBoardroom(dd, boardroomId);

        dbr.put("code", 200);
        dbr.put("msg", "success");
        return dbr;
    }

    @Override
    public JSONObject getBoardroomReservationOfDelete(long jobId) throws ParseException {
        JSONObject gbrod = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray tableData = new JSONArray();
        TimeUtil timeUtil = new TimeUtil();

        Date nowDate = new Date();
        Date nowTime = startTimeDeal.getDateByNowTime(nowDate);

        List<BoardroomReservation> reservations = new ArrayList<>();
        reservations = myCenterMapper.queryBoardroomReservationByJobId(jobId, nowTime);

        for(BoardroomReservation reservation : reservations) {
            JSONObject tdUnit = new JSONObject();

            Date date = reservation.getStartTime();
            String dateString = timeUtil.getFormatDateOfyMdHm(date);

            tdUnit.put("boardroomId", reservation.getBoardroomId());
            tdUnit.put("date", dateString);
            tdUnit.put("name", boardroomMapper.queryBoardroomNameById(reservation.getBoardroomId()));

            tableData.add(tdUnit);
        }
        data.put("tableData", tableData);

        gbrod.put("code", 200);
        gbrod.put("msg", "success");
        gbrod.put("data", data);
        return gbrod;
    }

    @Override
    public JSONObject privateCenterOfEmployee(long jobId) {
        JSONObject pcoe = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray list = new JSONArray();

        Employee employee = employeeMapper.queryEmployeeByJobId(jobId);
        String[] e = new String[] {"姓名", "工号", "部门", "组别"};
        for(int i=0; i<4; i++) {
            JSONObject listUnit = new JSONObject();

            listUnit.put("id", i+1);
            listUnit.put("item", e[i]);

            switch (i) {
                case 0:
                    listUnit.put("infor", employee.getName());
                    break;
                case 1:
                    listUnit.put("infor", employee.getJobId());
                    break;
                case 2:
                    listUnit.put("infor", employee.getDepartment());
                    break;
                case 3:
                    listUnit.put("infor", employee.getPosition());
                    break;
                default:
                    listUnit.put("error", "error");
            }

            list.add(listUnit);
        }
        data.put("list", list);

        pcoe.put("code", 200);
        pcoe.put("msg", "success");
        pcoe.put("data", data);
        return pcoe;
    }

    @Override
    public JSONObject getMyMeeting(long jobId) throws ParseException {
        JSONObject gmm = new JSONObject();
        JSONArray data = new JSONArray();
        TimeUtil timeUtil = new TimeUtil();

        Date nowDate = new Date();
        Date nowTime = startTimeDeal.getDateByNowTime(nowDate);

        List<BoardroomPartakeEmployee> partakeEmployees = new ArrayList<>();
        partakeEmployees = myCenterMapper.queryAllMeetingById(jobId, nowTime);

        for(BoardroomPartakeEmployee partakeEmployee : partakeEmployees) {
            JSONObject dataUnit = new JSONObject();
            long boardroomId = partakeEmployee.getPartakeConference();
            Boardroom boardroom = boardroomMapper.queryBoardroomById(boardroomId);
            String boardroomName = boardroom.getBoardroomName();

            dataUnit.put("boardroomId", boardroomId);
            dataUnit.put("boardroomName", boardroomName);
            dataUnit.put("time", timeUtil.getFormatDateOfyMdHm(partakeEmployee.getPartakeTime()));

            long originator = myCenterMapper.queryOriginatorId(boardroomId, partakeEmployee.getPartakeTime());
            dataUnit.put("originator", employeeMapper.queryEmployeeNameByJobId(originator));
            dataUnit.put("boardroomImg", boardroom.getBoardroomImg());

            if(originator == partakeEmployee.getJobId()) {
                dataUnit.put("flag", 1);
            } else {
                dataUnit.put("flag", 0);
            }

            data.add(dataUnit);

        }

        gmm.put("code", 200);
        gmm.put("msg", "success");
        gmm.put("data", data);
        return gmm;
    }

    @Override
    public JSONObject getMeetingEmployee(long boardroomId, String time, long jobId) throws ParseException {
        JSONObject gme = new JSONObject();
        JSONArray data = new JSONArray();
        TimeUtil timeUtil = new TimeUtil();

        long[] employees = myCenterMapper.queryMeetingEmployeeExceptOriginator(boardroomId, timeUtil.getFormatStringOfH(time), jobId);
        for (long e : employees) {
            JSONObject dataUnit = new JSONObject();
            dataUnit.put("jobId", e);
            dataUnit.put("name", employeeMapper.queryEmployeeNameByJobId(e));

            data.add(dataUnit);
        }

        gme.put("code", 200);
        gme.put("msg", "success");
        gme.put("data", data);
        return gme;
    }

    @Override
    public JSONObject insertMeetingEmployee(long jobId, long boardroomId, String time) throws ParseException {
        JSONObject ime = new JSONObject();
        TimeUtil timeUtil = new TimeUtil();

        Date date = timeUtil.getFormatStringOfH(time);
        BoardroomPartakeEmployee boardroomPartakeEmployee = new BoardroomPartakeEmployee();
        boardroomPartakeEmployee.setJobId(jobId);
        boardroomPartakeEmployee.setPartakeConference((int) boardroomId);
        boardroomPartakeEmployee.setPartakeTime(date);

        reservationMapper.insertReservationEmployee(boardroomPartakeEmployee);

        ime.put("code", 200);
        ime.put("msg", "success");
        return ime;
    }

    @Override
    public JSONObject deleteMeetingEmployee(long jobId, long boardroomId, String time) throws ParseException {
        JSONObject dme = new JSONObject();
        TimeUtil timeUtil = new TimeUtil();

        Date date = timeUtil.getFormatStringOfH(time);
        BoardroomPartakeEmployee boardroomPartakeEmployee = new BoardroomPartakeEmployee();
        boardroomPartakeEmployee.setJobId(jobId);
        boardroomPartakeEmployee.setPartakeConference((int) boardroomId);
        boardroomPartakeEmployee.setPartakeTime(date);

        reservationMapper.deleteReservationEmployee(boardroomPartakeEmployee);

        dme.put("code", 200);
        dme.put("msg", "success");
        return dme;
    }


}
