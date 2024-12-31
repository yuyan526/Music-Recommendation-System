package com.azl.recommendation_system.controller;

import com.azl.recommendation_system.pojo.Admin;
import com.azl.recommendation_system.service.IAdminService;
import com.azl.recommendation_system.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private IAdminService iAdminService;
    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    // 使用@RequestBody，前端传过来的数据必须是json格式，且json数据中的字段名要和对象的属性名一致，后端才能正确接收参数
    public Result adminLogin(@RequestBody Admin admin) {
        System.out.println(admin.toString());
        return iAdminService.adminLogin(admin);
    }
}
