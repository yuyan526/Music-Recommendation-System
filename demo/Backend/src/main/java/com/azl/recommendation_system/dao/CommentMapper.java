package com.azl.recommendation_system.dao;

import com.azl.recommendation_system.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository

public interface CommentMapper {
    /**
     * 根据歌曲id查找评论
     *
     * @param iid 歌曲id
     */
    List<Comment> querySongComment(@Param("iid") String iid);
    /**
     * 添加评论
     * @param comment  需要添加的评论
     */
    void addComment(Comment comment);
}
