package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Seagull_gby
 * @date 2019/2/27 16:06
 * Description: 会议室实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boardroom {

    /**
     * 会议室ID
     */
    private int boardroomId;

    /**
     * 会议室名字
     */
    private String boardroomName;

    /**
     * 会议室图片
     */
    private String boardroomImg;

    /**
     * 未来7天的预约情况
     */
    private String condition;
}
