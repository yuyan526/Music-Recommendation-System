package com.azl.recommendation_system.dao;

import com.azl.recommendation_system.pojo.SongSheet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface SongSheetMapper {
    /**
     * 根据用户id查找用户歌单
     * @param uid 用户id
     * @return
     */
    List<SongSheet> queryByUid(@Param("uid") String uid);

    /**
     * 根据歌单id查找用户歌单
     * @param sheetid 歌单id
     * @return
     */
    SongSheet queryBySheetid(@Param("sheetid") String sheetid);

    /**
     * 添加新歌单
     *
     * @param songsheet 歌单id，String类型
     * @return
     */
    void addSheet(SongSheet songsheet);

    /**
     * 更新用户信息
     * @param songsheet 新的用户信息
     */
    void updateSheet(SongSheet songsheet);

    /**
     * 根据用户id删除用户，将删除标志置为0
     *
     * @param sheetid 用户id
     */
    void deleteSheet(@Param("sheetid") String sheetid);


    /**
     * 获得指定条数的用户信息，根据发行时间高低排序，范围是：(currentPage-1)*pageSize后的pageSize个数据
     *
     * @param currentIndex 当前页面
     * @param pageSize     页面大小
     * @return pageSize个结果
     */
    List<SongSheet> querySheetsNew(@Param("currIndex") int currentIndex, @Param("pageSize") int pageSize);

    /**
     * 根据用户名查询用户名，模糊查询
     *
     * @param keyWord 搜索关键字
     * @return 查询结果
     */
    List<SongSheet> queryByKeyword(@Param("keyword") String keyWord);

    String querySongsheetsum();
}
