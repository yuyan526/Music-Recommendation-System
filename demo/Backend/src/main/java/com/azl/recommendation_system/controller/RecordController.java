package com.azl.recommendation_system.controller;

import com.alibaba.fastjson.JSON;
import com.azl.recommendation_system.pojo.Record;
import com.azl.recommendation_system.service.IRecordService;
import com.azl.recommendation_system.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordController {
    @Autowired
    IRecordService iRecordService;

    /**
     * 添加播放记录
     * @param record
     * @return
     */
    @RequestMapping(value = "/addRecord", method = RequestMethod.POST)
    public Result addRecord(@RequestBody Record record) {
        System.out.println(record);
        return iRecordService.addRecord(record);
    }

    /**
     * 获得用户的播放记录
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getRecordSong", method = RequestMethod.POST)
    public Result getRecord(@RequestBody String uid) {
        System.out.println(uid);
        String uid_str = JSON.parseObject(uid).get("uid").toString();
        //String uid_str = String.valueOf(JSON.parseObject(uid).get("uid"));
        return iRecordService.queryRecordByUid(uid_str);
    }
}
