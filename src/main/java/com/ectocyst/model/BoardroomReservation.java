package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Seagull_gby
 * @date 2019/2/27 18:52
 * Description: 会议室预约情况
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardroomReservation {

    /**
     * 会议室ID
     */
    private long boardroomId;

    /**
     * 会议日期
     */
    private String date;

    /**
     * 会议预约起始时间
     */
    private Date startTime;

    /**
     * 会议预约终止时间
     */
    private Date endTime;

    /**
     * 预约者工号
     */
    private long reservationId;
}
