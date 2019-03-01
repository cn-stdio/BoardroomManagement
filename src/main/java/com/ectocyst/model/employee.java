package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seagull_gby
 * @date 2019/2/27 18:55
 * Description: 人员实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class employee {

    /**
     * 工号
     */
    private int jobId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private int sex;

    /**
     * 职位
     */
    private String position;

    /**
     * 密码
     */
    private String password;

    /**
     * 预约的会议室编号
     */
    private String bookingConference;

    /**
     * 参与的会议室编号
     */
    private String partakeConference;
}
