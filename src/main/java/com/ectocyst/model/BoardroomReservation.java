package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int boardroomId;

    /**
     * 会议日期
     */
    private String date;

    /**
     * 会议时间段
     */
    private String time;

    /**
     * 预约者工号
     */
    private int reservation_id;
}
