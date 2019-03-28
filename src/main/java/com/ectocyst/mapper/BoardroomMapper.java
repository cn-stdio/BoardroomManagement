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
    @Select("SELECT * FROM boardroom ORDER BY boardroom_id")
    public List<Boardroom> queryBoardroom();

    /**
     * 查询目标会议室图片
     * @param boardroomId 会议室ID
     * @return 图片链接
     */
    @Select("SELECT boardroom_img FROM boardroom WHERE boardroom_id = #{boardroomId}")
    public String queryBoardroomImgById(long boardroomId);

    /**
     * 查询目标会议室名字
     * @param boardroomId 会议室ID
     * @return 名字
     */
    @Select("SELECT boardroom_name FROM boardroom WHERE boardroom_id = #{boardroomId}")
    public String queryBoardroomNameById(long boardroomId);

    /**
     * 查询目标会议室
     * @param boardroomId 会议室ID
     * @return 会议室实体类
     */
    @Select("SELECT * FROM boardroom WHERE boardroom_id = #{boardroomId}")
    public Boardroom queryBoardroomById(long boardroomId);
}
