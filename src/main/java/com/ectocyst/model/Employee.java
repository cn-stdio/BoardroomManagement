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
public class Employee {

    /**
     * 工号
     */
    private long jobId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（男1女2）
     */
    private int sex;

    /**
     * 职位
     */
    private String position;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 密码
     */
    private String password;

    /**
     * 人脸识别图片链接
     */
    private String imgUrl;

}
