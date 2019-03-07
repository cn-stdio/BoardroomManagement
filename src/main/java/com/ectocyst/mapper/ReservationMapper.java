package com.ectocyst.mapper;

import com.ectocyst.model.BoardroomReservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.Date;
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
    public List<BoardroomReservation> queryReservationById(long boardroomId);

    /**
     * 查找目标会议室在目标日期的预约起始时间
     * @param boardroomId 会议室ID
     * @param date 日期
     * @return 预约起始时间列表（从0点到23点）
     */
    @Select("SELECT start_time FROM boardroom_reservation WHERE boardroom_id = #{boardroomId} AND `date` = #{date} ORDER BY `start_time` DESC")
    public List<Date> queryReservationStartTimeByBoardroomIdAndDate(@Param("boardroomId") long boardroomId, @Param("date") String date);

    /**
     * 查找目标会议室在目标日期的预约结束时间
     * @param boardroomId 会议室ID
     * @param date 日期
     * @return 预约结束时间列表（从0点到23点）
     */
    @Select("SELECT end_time FROM boardroom_reservation WHERE boardroom_id = #{boardroomId} AND `date` = #{date} ORDER BY `end_time` DESC")
    public List<Date> queryReservationEndTimeByBoardroomIdAndDate(@Param("boardroomId") long boardroomId, @Param("date") String date);

}
