package com.azl.recommendation_system.service;

import com.azl.recommendation_system.pojo.User;
import com.azl.recommendation_system.util.result.Result;


public interface IUserService {
    /**
     * 管理员登录业务接口
     * @param user 传入一个user类型的对象，判断是否在数据库中
     * @return Result类型的结果，有状态码、时间戳等信息
     */
    Result userLogin(User user);

    /**
     * 添加用户
     * @param user 待添加的对象
     * @return Result类型的结果，有状态码、时间戳等信息
     */
    Result addUser(User user);

    /**
     * 更新用户信息
     * @param user 要更新的用户对象
     * @return
     */
    Result updateUser(User user);

    /**
     * 更新密码
     * @param uid 用户id
     * @param oldPw 旧密码
     * @param newPw 新密码
     * @return
     */
    Result updatePw(String uid, String oldPw, String newPw);

    /**
     * 获得用户的播放记录
     * @param uid 用户id
     * @return Result类型的结果，有状态码、时间戳等信息
     */
    Result getUserRecord(String uid);

    /**
     * 获得用户的推荐歌曲
     * @param uid 用户id
     * @return
     */
    Result getRecommendSongs(String uid);

    /**
     * 获得用户的相似音乐好友
     * @param uid 用户id
     * @return
     */
    Result getRecommendUsers(String uid);

    /**
     * 获得指定条数的用户信息，用于管理员管理
     * @param currIndex 起始位置，从0开始而不是1，所以currIndex=(currentPage-1)*pageSize
     * @param pageSize 返回的用户信息个数
     * @return List<User>类型的用户信息数组
     */
    Result queryUsersNew(int currIndex, int pageSize);

    /**
     * 根据用户名查询用户
     * @param keyWord 搜索关键词
     * @return 单个结果
     */
    Result queryByKeyword(String keyWord);

    /**
     * 根据用户id删除用户，单个删除
     * @param sheetid 要删除用户的id
     * @return
     */
    Result deleteUserById(String sheetid);
    /**
     * 添加游客
     * @param user 待添加的对象
     * @return Result类型的结果，有状态码、时间戳等信息
     */
    Result addVisitor(User user);
}
