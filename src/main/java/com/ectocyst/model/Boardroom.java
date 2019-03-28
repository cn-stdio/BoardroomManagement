package com.ectocyst.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private long boardroomId;

    /**
     * 会议室名字
     */
    private String boardroomName;

    /**
     * 会议室图片
     */
    private String boardroomImg;

    /**
     * 会议室容量
     */
    private String capacity;

    public long getBoardroomId() {
        return boardroomId;
    }

    public void setBoardroomId(long boardroomId) {
        this.boardroomId = boardroomId;
    }

    public String getBoardroomName() {
        return boardroomName;
    }

    public void setBoardroomName(String boardroomName) {
        this.boardroomName = boardroomName;
    }

    public String getBoardroomImg() {
        return boardroomImg;
    }

    public void setBoardroomImg(String boardroomImg) {
        this.boardroomImg = boardroomImg;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
