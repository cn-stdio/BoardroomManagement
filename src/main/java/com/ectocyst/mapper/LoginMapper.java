package com.ectocyst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Seagull_gby
 * @date 2019/3/24 13:55
 * Description: 登录数据库操作
 */

@Mapper
@Repository
public interface LoginMapper {

    /**
     * 检查当前工号是否被注册
     * @param jobId 工号
     * @return 次数
     */
    @Select("SELECT COUNT(*) FROM employee WHERE job_id = #{jobId}")
    public int queryEmployeeRegister(long jobId);
}
