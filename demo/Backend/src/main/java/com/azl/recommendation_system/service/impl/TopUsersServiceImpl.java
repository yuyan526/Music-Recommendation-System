package com.azl.recommendation_system.service.impl;

import com.azl.recommendation_system.dao.TopUsersMapper;
import com.azl.recommendation_system.dao.UserMapper;
import com.azl.recommendation_system.pojo.User;
import com.azl.recommendation_system.service.ITopUserService;
import com.azl.recommendation_system.util.result.Result;
import com.azl.recommendation_system.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class TopUsersServiceImpl implements ITopUserService {
    @Autowired
    private TopUsersMapper topUsersMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * 获得相似用户集合
     * @param uid 当前用户id
     * @return 相似用户集合，并封装到Result中
     */
    @Override
    public Result querySimilarUsers(String uid) {
        // 用户id列表
        List<String> uidList = Arrays.asList(topUsersMapper.queryByUid(uid).getTopUsers().split(","));
        // 相似用户列表
        List<User> userList = new ArrayList<>();
        // 根据用户id列表获得用户
        for (String tempId : uidList) {
            // 密码隐藏，并将用户添加到相似用户列表中
            userList.add(userMapper.queryById(tempId).setPassword("********"));
        }
        return ResultUtil.success(userList);
    }
}
