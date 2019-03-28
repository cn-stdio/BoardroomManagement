package com.ectocyst.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Seagull_gby
 * @date 2019/3/12 16:40
 * Description: 人员访问接口
 */

@Service
public interface EmployeeService {

    /**
     * 选择会议人员
     * @param jobId 工号
     * @return JSON
     */
    public JSONObject getReservationEmployee(long jobId);
}
