package com.azl.recommendation_system.service;

import com.azl.recommendation_system.pojo.Mysong;
import com.azl.recommendation_system.util.result.Result;

public interface IMysongService {
    /**
     * 根据歌单id查找歌单中的歌曲
     * @param sheetid 歌单id
     * @return 歌单列表
     */
    Result queryMysongBySheetid(String sheetid);
    /**
     * 添加歌曲到歌单中
     * @param mysong 新的歌曲
     * @return
     */
    Result addMySong(Mysong mysong);
    /**
     * 根据歌曲id和歌单id删除歌曲，单个删除
     * @param mysong 要删除歌曲
     * @return
     */
    Result deleteMysong(Mysong mysong);
}
