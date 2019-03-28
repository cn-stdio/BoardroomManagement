package com.ectocyst.mapper;

import com.ectocyst.model.BoardroomPartakeEmployee;
import com.ectocyst.model.BoardroomReservation;
import org.apache.ibatis.annotations.*;
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
     * 查找指定会议室在指定日期的所有预约情况
     * @param boardroomId 指定会议室
     * @param date 指定日期
     * @return 所有预约情况
     */
    @Select("SELECT * FROM boardroom_reservation WHERE boardroom_id = #{boardroomId} AND date = #{date}")
    public List<BoardroomReservation> queryReservationByIdAndDate(@Param("boardroomId") long boardroomId, @Param("date") String date);

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

    /**
     * 查询目标会议室在当前时间的预约数量
     * @param boardroomId 会议室ID
     * @param time 时间
     * @return 预约数量
     */
    @Select("SELECT COUNT(*) FROM boardroom_reservation WHERE boardroom_id = #{boardroomId} AND start_time <= #{time} AND end_time >= #{time}")
    public int queryCountByBoardroomIdAndTime(@Param("boardroomId") long boardroomId, @Param("time") Date time);

    /**
     * 查询目标会议室在当前时间的预约情况
     * @param boardroomId 会议室ID
     * @param time 时间
     * @return 预约情况
     */
    @Select("SELECT * FROM boardroom_reservation WHERE boardroom_id = #{boardroomId} AND start_time <= #{time} AND end_time >= #{time}")
    public BoardroomReservation queryReservationByBoardroomIdAndTime(@Param("boardroomId") long boardroomId, @Param("time") Date time);

    /**
     * 插入一条预约记录
     * @param boardroomReservation 预约会议室实体
     * @return 插入条数
     */
    @Insert("INSERT INTO boardroom_reservation(boardroom_id, date, start_time, reservation_id, end_time) VALUES(#{boardroomId}, #{date}, #{startTime}, #{reservationId}, #{endTime})")
    public int insertReservationBoardroom(BoardroomReservation boardroomReservation);

    /**
     * 插入人员信息
     * @param boardroomPartakeEmployee 人员
     * @return 插入条数
     */
    @Insert("INSERT INTO boardroom_partake_employee(job_id, partake_conference, partake_time) VALUES(#{jobId}, #{partakeConference}, #{partakeTime})")
    public int insertReservationEmployee(BoardroomPartakeEmployee boardroomPartakeEmployee);

    /**
     * 删除某条人员信息
     * @param boardroomPartakeEmployee 人员
     * @return 删除条数
     */
    @Delete("DELETE FROM boardroom_partake_employee WHERE job_id = #{jobId} AND partake_conference = #{partakeConference} AND partake_time = #{partakeTime}")
    public int deleteReservationEmployee(BoardroomPartakeEmployee boardroomPartakeEmployee);
}
