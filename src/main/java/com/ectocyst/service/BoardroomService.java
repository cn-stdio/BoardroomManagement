package com.ectocyst.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Seagull_gby
 * @date 2019/2/28 19:15
 * Description: 会议室操作接口
 */
@Service
public interface BoardroomService {

    /**
     * 查询所有会议室记录
     * @return 制定JSON
     */
    public JSONObject queryAllBoardroom();
}
