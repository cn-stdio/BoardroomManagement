package com.ectocyst.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Seagull_gby
 * @date 2019/3/25 10:57
 * Description: 权限控制数据库操作
 */

@Mapper
@Repository
public interface RoleMapper {

    /**
     * 插入权限与角色对应关系
     * @param jobId 工号（角色）
     * @param rolesId 权限ID
     * @return 插入条数
     */
    @Insert("INSERT INTO sys_user_role(sys_user_id, roles_id) VALUES(#{jobId}, #{rolesId})")
    public int insertRoleOfEmployee(@Param("jobId") long jobId, @Param("rolesId")long rolesId);
}
