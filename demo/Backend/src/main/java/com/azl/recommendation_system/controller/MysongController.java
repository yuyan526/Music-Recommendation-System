package com.azl.recommendation_system.controller;

import com.alibaba.fastjson.JSON;
import com.azl.recommendation_system.pojo.Mysong;
import com.azl.recommendation_system.service.IMysongService;
import com.azl.recommendation_system.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MysongController {
    @Autowired
    IMysongService iMysongService;

    /**
     * 获得用户歌单的歌曲列表
     *
     * @param sheetid
     * @return
     */
    @RequestMapping(value = "/getMySong", method = RequestMethod.POST)
    public Result getMySong(@RequestBody String sheetid) {
        System.out.println(sheetid);
        String sheetid_str = JSON.parseObject(sheetid).get("sheetid").toString();
        return iMysongService.queryMysongBySheetid(sheetid_str);
    }

    /**
     * 歌单中添加歌曲
     *
     * @param mysong 要添加的歌曲
     * @return
     */
    @RequestMapping(value = "/addMySong", method = RequestMethod.POST)
    public Result addMySong(@RequestBody Mysong mysong) {
        System.out.println(mysong);
        return iMysongService.addMySong(mysong);
    }

    /**
     * 删除
     *
     * @param mysong 要删除歌曲
     * @return
     */
    @RequestMapping(value = "/deleteMySong", method = RequestMethod.POST)
    public Result deleteMySong(@RequestBody Mysong mysong) {
        System.out.println(mysong);
        //String iid_str = JSON.parseObject(iid).get("iid").toString();
        return iMysongService.deleteMysong(mysong);
    }
}
