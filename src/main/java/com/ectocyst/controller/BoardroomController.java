package com.ectocyst.controller;

import com.ectocyst.service.BoardroomService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return returnBoardroom;
    }
}
