package com.azl.recommendation_system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.azl.recommendation_system.pojo.Comment;
import com.azl.recommendation_system.service.ICommentService;
import com.azl.recommendation_system.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    private ICommentService iCommentService;
    /**
     * 获得歌曲评论
     *
     * @param iid 歌曲id
     * @return 评论
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Result SongComment(@RequestBody String iid) {
        System.out.println(iid);
        JSONObject parse = JSON.parseObject(iid);
        return iCommentService.querySongComment(parse.getString("iid"));
    }

    /**
     * 增加评论
     *
     * @param  comment 前端数据封装成用户，添加到数据库中
     * @return 成功或者失败的提示信息，json格式
     */
    @RequestMapping(value = "/addcomment", method = RequestMethod.POST)
    public Result addComment(@RequestBody Comment comment) {
        System.out.println(comment);
        return iCommentService.addComment(comment);
    }
}

