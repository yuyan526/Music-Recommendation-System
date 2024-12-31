package com.azl.recommendation_system.service;

import com.azl.recommendation_system.pojo.Comment;
import com.azl.recommendation_system.util.result.Result;

public interface ICommentService {
    /**
     * 根据id查询歌曲评论
     * @param iid 歌曲iid
     * @return 单个结果
     */
    Result querySongComment(String iid);
    /**
     * 添加评论
     * @param comment 待添加的对象
     * @return Result类型的结果，有状态码、时间戳等信息
     */
    Result addComment(Comment comment);
}
