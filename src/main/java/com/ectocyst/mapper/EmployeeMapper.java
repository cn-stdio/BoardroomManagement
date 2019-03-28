package com.ectocyst.mapper;

import com.ectocyst.model.BoardroomPartakeEmployee;
import com.ectocyst.model.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/3/7 16:57
 * Description: 人员SQL操作类
 */

@Mapper
@Repository
public interface EmployeeMapper {

    /**
     * 查询所有人员记录
     * @return 人员集合
     */
    @Select("SELECT * FROM employee")
    public List<Employee> queryAllEmployee();

    /**
     * 查询所有人员记录（除了预约者）
     * @param jobId 工号
     * @return 人员集合
     */
    @Select("SELECT * FROM employee WHERE job_id != #{jobId}")
    public List<Employee> queryAllEmployeeExceptReservationPeople(long jobId);

    /**
     * 查询目标时间段目标会议室预约人数
     * @param partakeTime 目标时间段(起始时间)
     * @param partakeConference 会议室编号
     * @return 人数
     */
    @Select("SELECT COUNT(*) FROM boardroom_partake_employee WHERE partake_conference = #{partakeConference} AND partake_time = #{partakeTime}")
    public int queryCountByPartakeTime(@Param("partakeTime") Date partakeTime, @Param("partakeConference") long partakeConference);

    /**
     * 查询目标时间段目标会议室预约情况
     * @param partakeTime 目标时间段(起始时间)
     * @param partakeConference 会议室编号
     * @return 预约情况
     */
    @Select("SELECT * FROM boardroom_partake_employee WHERE partake_conference = #{partakeConference} AND partake_time = #{partakeTime}")
    public List<BoardroomPartakeEmployee> queryReservationByPartakeTime(@Param("partakeTime") Date partakeTime, @Param("partakeConference") long partakeConference);

    /**
     * 查询指定人员
     * @param jobId 工号
     * @return 指定人员信息
     */
    @Select("SELECT * FROM employee WHERE job_id = #{jobId}")
    public Employee queryEmployeeByJobId(long jobId);

    /**
     * 查询指定人员姓名
     * @param jobId 工号
     * @return 姓名
     */
    @Select("SELECT `name` FROM employee WHERE job_id = #{jobId}")
    public String queryEmployeeNameByJobId(long jobId);

    /**
     * 联结方式(未解决)查找指定人员及其权限
     * @param jobId 工号
     * @return 人员
     */

    @Select("SELECT * FROM employee WHERE job_id = #{jobId}")
    public Employee queryEmployByJobIdAndRole(long jobId);
    @Select("SELECT roles_id FROM sys_user_role WHERE sys_user_id = #{jobId}")
    public long queryRoleId(long jobId);
    @Select("SELECT name FROM sys_role WHERE id = #{id}")
    public String queryUserRole(long id);

    @Delete("UPDATE employee SET sex = null, position = null, department = null, password = null, img_url = null, phone = null WHERE job_id = #{jobId}")
    public int deleteEmployById(long jobId);
}
