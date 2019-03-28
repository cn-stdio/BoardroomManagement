package com.ectocyst.component;

import com.ectocyst.mapper.ReservationMapper;
import com.ectocyst.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/2/28 19:24
 * Description: 检验会议室预约
 */

@Component
public class TestReservation {

    @Autowired
    private ReservationMapper reservationMapper;

    /**
     * 判断当前时间段会议室是否正在被使用
     * @param boardroomId 会议室ID
     * @param date 日期
     * @return 当前时段是否被使用
     */
    public String testReservationByBoardroomIdAndDate(long boardroomId, String date) {

        String reservation = "NO";
        List<Date> startTimes = new ArrayList<>();
        List<Date> endTimes = new ArrayList<>();
        Date startTime = new Date();
        Date endTime = new Date();
        Date currentTime = new Date();

        startTimes = reservationMapper.queryReservationStartTimeByBoardroomIdAndDate(boardroomId, date);
        endTimes = reservationMapper.queryReservationEndTimeByBoardroomIdAndDate(boardroomId, date);

        for(int i=0; i<startTimes.size(); i++) {
            startTime = startTimes.get(i);
            endTime = endTimes.get(i);

            if(currentTime.getTime() >= startTime.getTime() && currentTime.getTime() <= endTime.getTime()) {
                reservation = "YES";
                break;
            } else {
                reservation = "NO";
            }
        }

        return reservation;
    }

    /**
     * 根据会议室编号获得当前时间的所有预约时间段
     * @param boardroomId 会议室编号
     * @return 某会议室预约起始时间集合
     */
    public List<Date> getReservationTimeOfCurrentDateByBoardroomId(long boardroomId) {

        TimeUtil timeUtil = new TimeUtil();
        String currentDate = null;

        try {
            currentDate = timeUtil.getFormatDateOfmd(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Date> startTimes = new ArrayList<>();
        startTimes = reservationMapper.queryReservationStartTimeByBoardroomIdAndDate(boardroomId, currentDate);

        return startTimes;
    }
}
