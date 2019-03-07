package com.ectocyst.service.impl;

import com.ectocyst.component.TestReservation;
import com.ectocyst.mapper.BoardroomMapper;
import com.ectocyst.mapper.ReservationMapper;
import com.ectocyst.model.Boardroom;
import com.ectocyst.service.BoardroomService;
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

    @Override
    public JSONObject queryAllBoardroom() {

        JSONObject returnBoardroom = new JSONObject();
        TestReservation testReservation = new TestReservation();
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
}
