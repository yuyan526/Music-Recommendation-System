package com.azl.recommendation_system.controller;

import com.azl.recommendation_system.pojo.Total;
import com.azl.recommendation_system.service.ITotalService;
import com.azl.recommendation_system.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class TotalController {
    @Autowired
    private ITotalService iTotalService;

    @RequestMapping(value = "/gettotal", method = RequestMethod.POST)
    // 使用@RequestBody，前端传过来的数据必须是json格式
    public Result gettotal(@RequestBody Total total) {
        System.out.println(total.toString());
        return iTotalService.gettotal(total);
    }

}
