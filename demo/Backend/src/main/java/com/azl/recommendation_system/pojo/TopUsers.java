package com.azl.recommendation_system.pojo;

import org.springframework.stereotype.Component;

@Component
public class TopUsers {
    // 用户id
    private String name;
    // 相似好友id集合
    private String topUsers;

    public TopUsers() {
    }

    public String getName() {
        return name;
    }

    public TopUsers setName(String name) {
        this.name = name;
        return this;
    }

    public String getTopUsers() {
        return topUsers;
    }

    public TopUsers setTopUsers(String topUsers) {
        this.topUsers = topUsers;
        return this;
    }

    @Override
    public String toString() {
        return "TopUsers{" +
                "name='" + name + '\'' +
                ", topUsers='" + topUsers + '\'' +
                '}';
    }
}
