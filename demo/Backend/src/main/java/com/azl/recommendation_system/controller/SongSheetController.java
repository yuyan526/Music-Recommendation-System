package com.azl.recommendation_system.controller;

import com.alibaba.fastjson.JSON;
import com.azl.recommendation_system.pojo.SongSheet;
import com.azl.recommendation_system.service.ISongSheetService;
import com.azl.recommendation_system.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SongSheetController {
    @Autowired
    private ISongSheetService iSongSheetService;

    /**
     * 用户歌单
     *
     * @param uid 前端传过来的用户id
     * @return 用户歌单数据，json格式
     */
    @RequestMapping(value = "/songSheets", method = RequestMethod.POST)
    public Result getUserSongSheet(@RequestBody String uid) {
        System.out.println("我的歌单，当前用户id：" + uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        return iSongSheetService.getSongSheet(uid_str);
    }

    /**
     * 添加歌单
     *
     * @param songsheet 要添加的歌单
     * @return
     */
    @RequestMapping(value = "/addSheet", method = RequestMethod.POST)
    public Result addSheet(@RequestBody SongSheet songsheet) {
        System.out.println(songsheet);
        return iSongSheetService.addSheet(songsheet);
    }

    /**
     * 更新歌单信息
     * @param songsheet 要更新的歌单信息
     * @return
     */
    @RequestMapping(value = "/updateSheet", method = RequestMethod.POST)
    public Result updateSheet(@RequestBody SongSheet songsheet) {
        System.out.println(songsheet);
        return iSongSheetService.updateSheet(songsheet);
    }

    /**
     * 删除
     *
     * @param sheetid 要删除歌单的id
     * @return
     */
    @RequestMapping(value = "/deleteSheet", method = RequestMethod.POST)
    public Result deleteSheet(@RequestBody String sheetid) {
        System.out.println(sheetid);
        String sheetid_str = JSON.parseObject(sheetid).get("sheetid").toString();
        return iSongSheetService.deleteSheetById(sheetid_str);
    }

    /**
     * 返回所有用户歌单信息
     *
     * @param currentPage 当前页面
     * @return
     */
    @RequestMapping(value = "/displaySheets", method = RequestMethod.POST)
    public Result sheetsNewLimit(@RequestBody String currentPage) {
        // 页面大小（偏移量）设置为10
        System.out.println("当前页面：" + currentPage + "页面大小：" + 10);
        // 获得当前页面的整数
        int num = Integer.parseInt(JSON.parseObject(currentPage).get("currentPage").toString());
        int currIndex = (num - 1) * 10;
        return iSongSheetService.querySheetsNew(currIndex, 10);
    }

    /**
     * 返回用户搜索的歌单数据
     *
     * @param keyword 搜索关键词
     * @return
     */
    @RequestMapping(value = "/searchSheets", method = RequestMethod.POST)
    public Result searchSheets(@RequestBody String keyword) {
        // 搜索关键词
        System.out.println("搜索关键词：" + keyword);
        // 获得搜索关键词
        String keywordStr = JSON.parseObject(keyword).get("keyword").toString();
        System.out.println("输入参数：" + keywordStr);
        return iSongSheetService.queryByKeyword(keywordStr);
    }
}
