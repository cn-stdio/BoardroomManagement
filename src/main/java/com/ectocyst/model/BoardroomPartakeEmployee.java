package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Seagull_gby
 * @date 2019/3/6 21:21
 * Description: 会议室参与人员实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardroomPartakeEmployee {

    /**
     * 参与人员工号
     */
    private long jobId;

    /**
     * 参与的会议室编号
     */
    private int partakeConference;

    /**
     * 参与会议起始时间
     */
    private Date partakeTime;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public int getPartakeConference() {
        return partakeConference;
    }

    public void setPartakeConference(int partakeConference) {
        this.partakeConference = partakeConference;
    }

    public Date getPartakeTime() {
        return partakeTime;
    }

    public void setPartakeTime(Date partakeTime) {
        this.partakeTime = partakeTime;
    }
}
