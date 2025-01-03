package com.azl.recommendation_system.pojo;

import org.springframework.stereotype.Component;

/**
 * @author azlChaojie
 * @Description: TODO(相似歌曲推荐)
 * @date 2021/4/26 21:54
 */
@Component
public class TopSongs {
    // 歌曲id
    private String songName;
    // 相似歌曲的id合集
    private String topSongs;

    public TopSongs() {
    }

    public String getSongName() {
        return songName;
    }

    public TopSongs setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getTopSongs() {
        return topSongs;
    }

    public TopSongs setTopSongs(String topSongs) {
        this.topSongs = topSongs;
        return this;
    }

    @Override
    public String toString() {
        return "TopSongs{" +
                "songName='" + songName + '\'' +
                ", topSongs='" + topSongs + '\'' +
                '}';
    }
}
