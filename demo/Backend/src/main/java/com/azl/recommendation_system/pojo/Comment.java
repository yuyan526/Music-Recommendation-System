package com.azl.recommendation_system.pojo;

import org.springframework.stereotype.Component;

@Component
public class Comment {
    // 用户名
    private String name;
    // 用户id
    private String sid;
    // 该用户评论
    private String comments;
    // 歌id
    private String iid;

    public Comment() {
    }
    public String getName() {
        return name;
    }

    public Comment setName(String name) {
        this.name = name;
        return this;
    }

    public String getUid() {
        return sid;
    }

    public Comment setUid(String sid) {
        this.sid = sid;
        return this;
    }

    public String getComment() {
        return comments;
    }

    public Comment setComment(String comments) {
        this.comments = comments;
        return this;
    }

    public String getIid() {
        return iid;
    }

    public Comment setIid(String iid) {
        this.iid = iid;
        return this;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "iid='" + iid + '\'' +
                "name='" + name + '\'' +
                ", sid='" + sid + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
