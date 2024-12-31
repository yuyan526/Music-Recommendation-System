package com.azl.recommendation_system.service.impl;

import com.azl.recommendation_system.dao.MysongMapper;
import com.azl.recommendation_system.dao.SongMapper;
import com.azl.recommendation_system.pojo.Mysong;
import com.azl.recommendation_system.pojo.Song;
import com.azl.recommendation_system.service.IMysongService;
import com.azl.recommendation_system.util.httpUtil.SongDetail;
import com.azl.recommendation_system.util.result.Result;
import com.azl.recommendation_system.util.result.ResultUtil;
import com.azl.recommendation_system.util.timeUtil.MyTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MysongServiceImpl implements IMysongService{
    @Autowired
    MysongMapper mysongMapper;
    @Autowired
    SongMapper songMapper;

    @Override
    public Result queryMysongBySheetid(String sheetid) {
        // 判空
        if (sheetid == null || sheetid.length() == 0) {
            return ResultUtil.fail(420, "获取失败，传入参数为空");
        }
        // 获得查询结果
        List<Mysong> mysongs = mysongMapper.queryBySheetid(sheetid);
        // 判空
        if (mysongs == null) {
            return ResultUtil.fail(420, "获取失败，未查询到结果");
        }
        // 歌单歌曲集合
        List<Song> songs = new ArrayList<>();
        for (Mysong mysong : mysongs) {
            Song song = songMapper.queryByIid(mysong.getIid());
            // 设置音乐时长
            song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
            // 设置音乐播放链接
            song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
            // 添加到结果数组中
            songs.add(song);
        }
        return ResultUtil.success(songs);
    }

    @Override
    public Result addMySong(Mysong mysong) {
        // 判空
        if (mysong != null) {
            // 查询该歌曲是否在该歌单中存在，存在则添加
            Song res = songMapper.queryBySongnameAndSingername(mysong.getSongName(), mysong.getSingerName());
            if (res == null) {
                return ResultUtil.fail(430, "该歌曲不存在，无法添加，请稍后重试！");
            }
            mysong.setIid(res.getIid());
            Mysong res1 = mysongMapper.queryBySheetidAndIid(mysong.getSheetid(), mysong.getIid());
            if (res1 != null) {
                return ResultUtil.fail(430, "该歌曲id已存在，无法添加，请稍后重试！");
            }
            mysongMapper.addMySong(mysong);
            // 添加成功，返回提示信息
            return ResultUtil.success(mysong);
        } else {
            return ResultUtil.fail(420, "歌曲信息为空，添加失败，请稍后再试！");
        }
    }

    @Override
    public Result deleteMysong(Mysong mysong) {
        if (mysong != null) {
            Mysong res = mysongMapper.queryBySheetidAndIid(mysong.getSheetid(), mysong.getIid());
            System.out.println(res);
            // 歌曲存在于歌单中才能删除
            if (res == null) {
                return ResultUtil.fail(430, "该歌曲或歌单不存在，无法删除，请稍后重试！");
            }
            mysongMapper.deleteMysong(mysong);
            // 删除成功，返回提示信息
            return ResultUtil.success(mysong);
        } else {
            return ResultUtil.fail(420, "歌单id为空，删除失败，请稍后再试！");
        }
    }

}
