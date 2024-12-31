package com.azl.recommendation_system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.azl.recommendation_system.pojo.User;
import com.azl.recommendation_system.service.IUserService;
import com.azl.recommendation_system.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     *
     * @param user 前端数据封装成用户，查询数据验证
     * @return 成功或者失败的提示信息，json格式
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    // 使用@RequestBody，前端传过来的数据必须是json格式
    public Result userLogin(@RequestBody User user) {
        System.out.println(user.toString());
        return iUserService.userLogin(user);
    }

    /**
     * 用户注册
     *
     * @param user 前端数据封装成用户，添加到数据库中
     * @return 成功或者失败的提示信息，json格式
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result addUser(@RequestBody User user) {
        System.out.println(user);
        return iUserService.addUser(user);
    }

    /**
     * 相似音乐好友
     *
     * @param uid 前端传过来的用户id
     * @return 相似音乐好友数据，json格式
     */
    @RequestMapping(value = "/recommendUsers", method = RequestMethod.POST)
    public Result getUserRecommendUsers(@RequestBody String uid) {
        System.out.println("好友推荐，当前用户id：" + uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        return iUserService.getRecommendUsers(uid_str);
    }

    /**
     * 音乐推荐
     *
     * @param uid 前端传过来的用户id
     * @return 相似音乐好友数据，json格式
     */
    @RequestMapping(value = "/recommendSongs", method = RequestMethod.POST)
    public Result getUserRecommendSongs(@RequestBody String uid) {
        System.out.println("音乐推荐，当前用户id：" + uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        return iUserService.getRecommendSongs(uid_str);
    }

    /**
     * 更新用户信息
     * @param user 要更新的用户信息
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Result updateUser(@RequestBody User user) {
        System.out.println(user);
        return iUserService.updateUser(user);
    }

    /**
     * 更新密码
     * @param userInfo 用户部分信息，包括用户id，旧密码，新密码
     * @return
     */
    @RequestMapping(value = "/updatePw",method = RequestMethod.POST)
    public Result updatePs(@RequestBody String userInfo) {
        System.out.println(userInfo);
        // 解析json数据
        JSONObject user_json = JSON.parseObject(userInfo);
        String uid = user_json.getString("uid");
        String oldPw = user_json.getString("oldPw");
        String newPw = user_json.getString("newPw");
        // 更新密码
        return iUserService.updatePw(uid, oldPw, newPw);
    }

    /**
     * 返回所有用户信息
     *
     * @param currentPage 当前页面
     * @return
     */
    @RequestMapping(value = "/displayUsers", method = RequestMethod.POST)
    public Result usersNewLimit(@RequestBody String currentPage) {
        // 页面大小（偏移量）设置为10
        System.out.println("当前页面：" + currentPage + "页面大小：" + 10);
        // 获得当前页面的整数
        int num = Integer.parseInt(JSON.parseObject(currentPage).get("currentPage").toString());
        int currIndex = (num - 1) * 10;
        return iUserService.queryUsersNew(currIndex, 10);
    }

    /**
     * 返回用户搜索的用户数据
     *
     * @param keyword 搜索关键词
     * @return
     */
    @RequestMapping(value = "/searchUsers", method = RequestMethod.POST)
    public Result searchUsers(@RequestBody String keyword) {
        // 搜索关键词
        System.out.println("搜索关键词：" + keyword);
        // 获得搜索关键词
        String keywordStr = JSON.parseObject(keyword).get("keyword").toString();
        System.out.println("输入参数：" + keywordStr);
        return iUserService.queryByKeyword(keywordStr);
    }

    /**
     * 删除
     *
     * @param uid 要删除用户的id
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public Result deleteUser(@RequestBody String uid) {
        System.out.println(uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        return iUserService.deleteUserById(uid_str);
    }

    /**
     * 游客特殊注册
     *
     * @param user 前端数据封装成游客，添加到数据库中
     * @return 成功或者失败的提示信息，json格式
     */
    @RequestMapping(value = "/registerVisitor", method = RequestMethod.POST)
    public Result addVisitor(@RequestBody User user) {
        System.out.println(user);
        return iUserService.addVisitor(user);
    }
}


