package com.ectocyst.mapper;

import com.ectocyst.model.Boardroom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/2/28 19:09
 * Description: 会议室数据库操作类
 */
@Mapper
@Repository
public interface BoardroomMapper {

    /**
     * 查询所有会议室记录
     * @return 所有会议室
     */
    @Select("SELECT * FROM boardroom")
    public List<Boardroom> queryBoardroom();
}
