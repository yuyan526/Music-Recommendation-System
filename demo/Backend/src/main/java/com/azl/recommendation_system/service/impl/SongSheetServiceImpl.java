package com.azl.recommendation_system.service.impl;

import com.azl.recommendation_system.dao.SongSheetMapper;
import com.azl.recommendation_system.dao.UserMapper;
import com.azl.recommendation_system.pojo.SongSheet;
import com.azl.recommendation_system.util.RandomUtil;
import com.azl.recommendation_system.service.ISongSheetService;
import com.azl.recommendation_system.util.result.Result;
import com.azl.recommendation_system.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SongSheetServiceImpl implements ISongSheetService {
    @Autowired
    private SongSheetMapper songsheetMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public Result getSongSheet(String uid) {
        // 用户歌单数组
        List<SongSheet> songSheet = songsheetMapper.queryByUid(uid);
        if (songSheet == null) {
            return ResultUtil.fail(500, "获取歌单失败！");
        }
        // 返回用户歌单
        return ResultUtil.success(songSheet);
    }

    @Override
    public Result addSheet(SongSheet songsheet) {
        // 判空
        if (songsheet != null) {
            // 设置歌单随机id
            songsheet.setSheetid(RandomUtil.getNBitRandomDigit(9));
            System.out.println(songsheet);
            //
            SongSheet res = songsheetMapper.queryBySheetid(songsheet.getSheetid());
            // 歌单不存在才能添加
            if (res != null) {
                return ResultUtil.fail(430, "该歌单id已存在，无法添加，请稍后重试！");
            }
            songsheetMapper.addSheet(songsheet);
            // 添加成功，返回提示信息
            return ResultUtil.success(songsheet);
        } else {
            return ResultUtil.fail(420, "歌单信息为空，添加失败，请稍后再试！");
        }
    }

    @Override
    public Result updateSheet(SongSheet songsheet) {
        // 判空
        if (songsheet != null) {
            SongSheet res = songsheetMapper.queryBySheetid(songsheet.getSheetid());
            if (res == null) {
                return ResultUtil.fail(430, "该歌单不存在，更新失败，请稍后重试！");
            }
            songsheetMapper.updateSheet(songsheet);
            // 更新成功则返回
            return ResultUtil.success(songsheet);
        } else {
            return ResultUtil.fail(430, "更新失败，请稍后再试！");
        }
    }

    @Override
    public Result deleteSheetById(String sheetid) {
        if (sheetid != null) {
            SongSheet res = songsheetMapper.queryBySheetid(sheetid);
            // 歌单存在才能删除
            if (res == null) {
                return ResultUtil.fail(430, "该歌单不存在，无法删除，请稍后重试！");
            }
            songsheetMapper.deleteSheet(sheetid);
            // 删除成功，返回提示信息
            return ResultUtil.success(sheetid);
        } else {
            return ResultUtil.fail(420, "歌单id为空，删除失败，请稍后再试！");
        }
    }

    @Override
    public Result querySheetsNew(int currIndex, int pageSize) {
        // 得到结果
        List<SongSheet> sheetList = songsheetMapper.querySheetsNew(currIndex, pageSize);
        if (sheetList == null) {
            return ResultUtil.fail(500, "数据不存在！");
        }
        // 返回用户数据
        return ResultUtil.success(sheetList);
    }

    @Override
    public Result queryByKeyword(String keyWord) {
        if (keyWord == null || "".equals(keyWord)) {
            return ResultUtil.fail(400, "未搜索到数据！");
        }
        // 从数据库查询数据
        List<SongSheet> songsheets = songsheetMapper.queryByKeyword("%" + keyWord + "%");
        return ResultUtil.success(songsheets);
    }
}

