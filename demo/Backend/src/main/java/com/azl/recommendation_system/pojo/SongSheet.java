package com.azl.recommendation_system.pojo;

import org.springframework.stereotype.Component;

@Component
public class SongSheet {
    //用户 id
    private String uid;
    //歌单 id
    private String sheetid;
    //歌单名
    private  String sheetname;

    public SongSheet() {
    }

    public String getUid() {
        return uid;
    }

    public SongSheet setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getSheetid() {
        return sheetid;
    }

    public SongSheet setSheetid(String sheetid) {
        this.sheetid = sheetid;
        return this;
    }

    public  String getSheetName() {
        return sheetname;
    }

    public  SongSheet setSheetName(String sheetname){
        this.sheetname = sheetname;
        return this;
    }


    @Override
    public String toString() {
        return "SongSheet{" +
                "uid='" + uid + '\'' +
                ", sheetid='" + sheetid + '\'' +
                ", sheetname='" + sheetname + '\'' +
                '}';
    }

}
