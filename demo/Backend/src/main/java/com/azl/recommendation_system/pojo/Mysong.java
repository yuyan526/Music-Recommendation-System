package com.azl.recommendation_system.pojo;

import org.springframework.stereotype.Component;

@Component
public class Mysong {
    //歌单id
    private String sheetid;
    //歌曲id
    private String iid;
    // 歌曲名称
    private String songName;
    // 歌手名称
    private String singerName;

    public Mysong() {
    }

    public String getSheetid() {
        return sheetid;
    }

    public Mysong setSheetid(String sheetid) {
        this.sheetid = sheetid;
        return this;
    }

    public String getIid() {
        return iid;
    }

    public Mysong setIid(String iid) {
        this.iid = iid;
        return this;
    }

    public String getSongName() {
        return songName;
    }

    public Mysong setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getSingerName() {
        return singerName;
    }

    public Mysong setSingerName(String singerName) {
        this.singerName = singerName;
        return this;
    }

    @Override
    public String toString() {
        return "Mysong{" +
                "sheetid='" + sheetid + '\'' +
                ", iid='" + iid + '\'' +
                ", songName='" + songName + '\'' +
                ", singerName='" + singerName + '\'' +
                '}';
    }
}
