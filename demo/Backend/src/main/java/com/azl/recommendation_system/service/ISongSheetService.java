package com.azl.recommendation_system.service;

import com.azl.recommendation_system.pojo.SongSheet;
import com.azl.recommendation_system.util.result.Result;

public interface ISongSheetService {
    /**
     * 根据当前用户id查找用户歌单
     * @param uid 当前用户id
     * @return 用户歌单集合
     */
    Result getSongSheet(String uid);

    /**
     * 添加新的歌单到数据库中
     * @param songsheet 新的歌曲
     * @return
     */
    Result addSheet(SongSheet songsheet);

    /**
     * 更新歌单信息
     * @param songsheet 要更新的歌单对象
     * @return
     */
    Result updateSheet(SongSheet songsheet);

    /**
     * 根据歌单id删除歌单，单个删除
     * @param sheetid 要删除歌单的id
     * @return
     */
    Result deleteSheetById(String sheetid);

    /**
     * 获得指定条数的歌单信息，用于管理员管理
     * @param currIndex 起始位置，从0开始而不是1，所以currIndex=(currentPage-1)*pageSize
     * @param pageSize 返回的歌单信息个数
     * @return List<User>类型的歌单信息数组
     */
    Result querySheetsNew(int currIndex, int pageSize);

    /**
     * 根据歌单名查询歌单
     * @param keyWord 搜索关键词
     * @return 单个结果
     */
    Result queryByKeyword(String keyWord);

}

