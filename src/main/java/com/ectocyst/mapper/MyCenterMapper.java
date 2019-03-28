package com.ectocyst.mapper;

import com.ectocyst.model.Boardroom;
import com.ectocyst.model.BoardroomPartakeEmployee;
import com.ectocyst.model.BoardroomReservation;
import com.ectocyst.model.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/3/25 12:17
 * Description: 个人中心数据库操作
 */

@Mapper
@Repository
public interface MyCenterMapper {

    /**
     * 更新人员信息（在指定工号处）
     * @param employee 人员
     * @return 更新数据条目
     */
    @Update("UPDATE employee SET name = #{name}, sex = #{sex}, position = #{position}, department = #{department}, img_url = #{imgUrl}, phone = #{phone} WHERE job_id = #{jobId}")
    public int updateEmployee(Employee employee);

    /**
     * 添加待注册人员信息
     * @param jobId 工号
     * @param name 名称
     * @return 插入记录条数
     */
    @Insert("INSERT INTO employee(job_id, name) VALUES(#{jobId}, #{name})")
    public int insertBasicEmployee(@Param("jobId") long jobId, @Param("name") String name);

    /**
     * 删除指定人员信息
     * @param jobId 工号
     * @return 删除条数
     */
    @Delete("DELETE FROM employee WHERE job_id = #{jobId}")
    public int deleteEmployeeById(long jobId);

    /**
     * 删除指定人员权限信息
     * @param jobId 工号
     * @return 删除条数
     */
    @Delete("DELETE FROM sys_user_role WHERE sys_user_id = #{jobId}")
    public int deleteEmployeeRoleById(long jobId);

    /**
     * 更新指定会议室记录
     * @param boardroom 会议室
     * @return 更新条数
     */
    @Update("UPDATE boardroom SET boardroom_name = #{boardroomName}, boardroom_img = #{boardroomImg}, capacity = #{capacity} WHERE boardroom_id = #{boardroomId}")
    public int updateBoardroomById(Boardroom boardroom);

    /**
     * 添加一条会议室记录
     * @param boardroom 会议室
     * @return 添加条数
     */
    @Insert("INSERT INTO boardroom(boardroom_id, boardroom_name, boardroom_img, capacity) VALUES(#{boardroomId}, #{boardroomName}, #{boardroomImg}, #{capacity})")
    public int insertBoardroom(Boardroom boardroom);

    /**
     * 删除指定会议室记录
     * @param boardroomId 会议室ID
     * @return 删除条数
     */
    @Delete("DELETE FROM boardroom WHERE boardroom_id = #{boardroomId}")
    public int deleteBoardroomById(long boardroomId);

    /**
     * 删除指定会议室预约人员及参与人员
     * @param date 时间
     * @param boardroomId 会议室ID
     * @return 删除条数
     */
    @Delete("DELETE FROM boardroom_partake_employee WHERE partake_time = #{date} AND partake_conference = #{boardroomId}")
    public int deleteReservationEmployeeOfBoardroom(@Param("date") Date date, @Param("boardroomId") long boardroomId);

    /**
     * 删除会议室预约信息
     * @param date 时间
     * @param boardroomId 会议室ID
     * @return 删除条数
     */
    @Delete("DELETE FROM boardroom_reservation WHERE start_time = #{date} AND boardroom_id = #{boardroomId}")
    public int deleteReservationBoardroom(@Param("date") Date date, @Param("boardroomId") long boardroomId);

    /**
     * 查询指定人员所有预约会议室记录
     * @param jobId 工号
     * @param nowTime 现在时间
     * @return 结果集合
     */
    @Select("SELECT * FROM boardroom_reservation WHERE reservation_id = #{jobId} AND start_time > #{nowTime} ORDER BY boardroom_id")
    public List<BoardroomReservation> queryBoardroomReservationByJobId(@Param("jobId") long jobId, @Param("nowTime") Date nowTime);

    /**
     * 查询所有预约及参与的会议
     * @param jobId 工号
     * @param nowTime 现在时间
     * @return 结果集合
     */
    @Select("SELECT * FROM boardroom_partake_employee WHERE job_id = #{jobId} AND partake_time >= #{nowTime} ORDER BY partake_conference")
    public List<BoardroomPartakeEmployee> queryAllMeetingById(@Param("jobId") long jobId, @Param("nowTime") Date nowTime);

    /**
     * 查询指定预约记录的预约者工号
     * @param boardroomId 会议室编号
     * @param startTime 起始时间
     * @return 预约者工号
     */
    @Select("SELECT reservation_id FROM boardroom_reservation WHERE boardroom_id = #{boardroomId} AND start_time = #{startTime}")
    public long queryOriginatorId(@Param("boardroomId") long boardroomId, @Param("startTime") Date startTime);

    /**
     * 查找指定会议室指定时间段参会所有人员（不包括预约者）
     * @param boardroomId 会议室编号
     * @param time 起始时间
     * @param jobId 预约者工号
     * @return 结果数组
     */
    @Select("SELECT job_id FROM boardroom_partake_employee WHERE partake_conference = #{boardroomId} AND partake_time = #{time} AND job_id != #{jobId}")
    public long[] queryMeetingEmployeeExceptOriginator(@Param("boardroomId") long boardroomId, @Param("time") Date time, @Param("jobId") long jobId);
}
