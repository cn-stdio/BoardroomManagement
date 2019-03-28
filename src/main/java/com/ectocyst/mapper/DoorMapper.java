package com.ectocyst.mapper;

import com.ectocyst.model.Door;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Seagull_gby
 * @date 2019/3/27 20:41
 * Description:
 */

@Mapper
@Repository
public interface DoorMapper {

    @Select("SELECT * FROM door")
    public Door queryDoor();

    @Update("UPDATE door SET sta = #{sta}, flag = #{flag}")
    public int updateDoor(@Param("sta") int sta, @Param("flag") int flag);

}
