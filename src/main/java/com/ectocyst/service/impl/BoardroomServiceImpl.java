package com.ectocyst.service.impl;

import com.ectocyst.component.TestReservation;
import com.ectocyst.mapper.BoardroomMapper;
import com.ectocyst.mapper.ReservationMapper;
import com.ectocyst.model.Boardroom;
import com.ectocyst.model.BoardroomReservation;
import com.ectocyst.service.BoardroomService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public JSONObject queryAllBoardroom() {

        JSONObject returnBoardroom = new JSONObject();
        TestReservation testReservation = new TestReservation();

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

        });

        return returnBoardroom;
    }
}
