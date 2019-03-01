package com.ectocyst.mapper;

import com.ectocyst.model.BoardroomReservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/2/28 19:28
 * Description: 预约数据库操作类
 */

@Mapper
@Repository
public interface ReservationMapper {

    /**
     * 查找指定会议室所有预约情况
     * @param boardroomId 指定会议室
     * @return 所有预约情况
     */
    @Select("SELECT * FROM boardroom_reservation WHERE boardroom_id = #{boardroomId}")
    public List<BoardroomReservation> queryReservationById(int boardroomId);
}
