package com.azl.recommendation_system.dao;

import com.azl.recommendation_system.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    /**
     * 根据用户id查找用户
     * @param uid 用户id
     * @return 用户实例，User类型
     */
    User queryById(@Param("uid") String uid);

    /**
     * 查找所有用户信息
     * @return 用户实例集合，List<User>类型
     */
    List<User> queryAll();

    /**
     * 添加用户
     * @param user 需要添加的用户
     */
    void addUser(User user);

    /**
     * 更新用户信息
     * @param user 新的用户信息
     */
    void updateUser(User user);

    /**
     * 更新密码
     * @param uid 用户id
     * @param newPs 新密码
     */
    void updatePs(@Param("uid") String uid, @Param("newPs") String newPs);

    /**
     * 获得指定条数的用户信息，根据发行时间高低排序，范围是：(currentPage-1)*pageSize后的pageSize个数据
     *
     * @param currentIndex 当前页面
     * @param pageSize     页面大小
     * @return pageSize个结果
     */
    List<User> queryUsersNew(@Param("currIndex") int currentIndex, @Param("pageSize") int pageSize);

    /**
     * 根据用户名查询用户名，模糊查询
     *
     * @param keyWord 搜索关键字
     * @return 查询结果
     */
    List<User> queryByKeyword(@Param("keyword") String keyWord);

    /**
     * 根据用户id删除用户，将删除标志置为0
     *
     * @param uid 用户id
     */
    void deleteUser(@Param("uid") String uid);

    String queryUsersum();
}
