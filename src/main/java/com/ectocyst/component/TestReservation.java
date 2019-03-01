package com.ectocyst.component;

import com.ectocyst.model.BoardroomReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/2/28 19:24
 * Description: 检验七天内的预约
 */

@Component
public class TestReservation {

    /**
     * 检验会议室预约情况
     * @param boardroomReservations 会议室集合
     * @param date 日期
     * @return 某日期是否被预约
     */
    public String testReservationByBoardroomIdAndDate(List<BoardroomReservation> boardroomReservations, String date) {
        String reservation = null;
        List<String> reservationTime = new ArrayList<>();

        boardroomReservations.forEach(boardroomReservation -> {
            if(boardroomReservation.getDate().equals(date)) {
                reservationTime.add(boardroomReservation.getTime());
            }
        });

        /* 判断一天时间是否被占满 */


        return reservation;
    }
}
