package com.ectocyst.service;

import com.ectocyst.model.Employee;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Seagull_gby
 * @date 2019/3/24 14:05
 * Description: 注册接口
 */

@Service
public interface RegisterService {

    /**
     * 注册人员信息
     * @param employee 人员
     * @return JSON
     */
    public JSONObject registerEmployee(Employee employee) throws Exception;
}
