package com.azl.recommendation_system.service.impl;

import com.azl.recommendation_system.dao.CommentMapper;
import com.azl.recommendation_system.pojo.Comment;
import com.azl.recommendation_system.service.ICommentService;
import com.azl.recommendation_system.util.result.Result;
import com.azl.recommendation_system.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    CommentMapper commentMapper;



    @Override
    public Result querySongComment(String iid) {
        // 判空
        if (iid == null || iid.length() == 0) {
            return ResultUtil.fail(420, "获取失败，传入参数为空");
        }
        // 获得查询结果
        List<Comment> comment = commentMapper.querySongComment(iid);
        System.out.println(comment);
        // 判空
        if (comment == null) {
            return ResultUtil.fail(420, "获取失败，未查询到结果");
        }

        return ResultUtil.success(comment);
    }

    @Override
    public Result addComment(Comment comment) {
        if (comment != null) {
            commentMapper.addComment(comment);
            return ResultUtil.success();
        }
        return ResultUtil.fail(420, "添加失败，请稍后重试！");
    }
}
