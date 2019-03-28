package com.ectocyst.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Seagull_gby
 * @date 2019/3/24 13:52
 * Description: 登录接口
 */

@Service
public interface LoginService {

    /**
     * 检查目标工号是否被注册过
     * @param jobId 工号
     * @return JSON
     */
    public JSONObject jobIdRegisterCheck(long jobId);

    /**
     * 检查是否登录成功
     * @param jobId 工号
     * @param password 密码
     * @return JSON
     */
    public JSONObject loginCheck(long jobId, String password);

    /**
     * 人脸比对
     * @param base base64图片格式
     * @return JSON
     */
    public void faceDetection(String base);
}
