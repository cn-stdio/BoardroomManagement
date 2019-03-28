package com.ectocyst.mapper;

import com.ectocyst.model.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Seagull_gby
 * @date 2019/3/24 14:11
 * Description: 注册数据库操作
 */

@Mapper
@Repository
public interface RegisterMapper {

    /**
     * 插入人员信息（在指定工号处）
     * @param employee 人员
     * @return 插入数据条目
     */
    @Update("UPDATE employee SET sex = #{sex}, position = #{position}, department = #{department}, password = #{password}, img_url = #{imgUrl}, phone = #{phone} WHERE job_id = #{jobId}")
    public int insertEmployee(Employee employee);
}
