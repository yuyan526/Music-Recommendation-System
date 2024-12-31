package com.azl.recommendation_system.dao;

import com.azl.recommendation_system.pojo.Mysong;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MysongMapper {
    /**
     * 根据歌单id查找该歌单中的歌曲
     * @param sheetid 歌单id
     * @return 歌单中歌曲集合，List<Mysong>类型
     */
    List<Mysong> queryBySheetid(@Param("sheetid") String sheetid);

    /**
     * 根据歌单id和歌曲id查找对应信息
     * @param sheetid 歌单id
     * @param iid 歌曲id
     * @return 对应的一条记录
     */
    Mysong queryBySheetidAndIid(@Param("sheetid") String sheetid, @Param("iid") String iid);

    /**
     * 在歌单中添加新歌曲
     *
     * @param mysong 歌曲iid，String类型
     * @return Mysong实例
     */
    void addMySong(Mysong mysong);

    /**
     * 根据id删除歌单中的歌曲，将删除标志置为0
     *
     * @param mysong 歌单中的歌曲
     */
    void deleteMysong(Mysong mysong);
}
