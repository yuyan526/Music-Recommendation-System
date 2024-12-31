package com.azl.recommendation_system.service.impl;

import com.azl.recommendation_system.dao.*;
import com.azl.recommendation_system.pojo.*;
import com.azl.recommendation_system.service.ITotalService;
import com.azl.recommendation_system.util.result.Result;
import com.azl.recommendation_system.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TotalServiceImpl implements ITotalService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private TopSongsMapper topSongsMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SongSheetMapper songsheetMapper;
    @Autowired
    private TopUsersMapper topUsersMapper;
    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Result gettotal(Total total) {
        List<Total> tt = new ArrayList<>();
        if (total != null) {
            total.settotalsongs(songMapper.querySongsum());
            total.settotalusers(userMapper.queryUsersum());
            total.settotalsheets(songsheetMapper.querySongsheetsum());
            total.settotalsingers(singerMapper.querySingersum());
            total.settotaladmins(adminMapper.queryAdminsum());
            System.out.println(total);
            tt = Arrays.asList(total);
            System.out.println(tt);
            if (tt != null) {
                return ResultUtil.success(tt);
            } else {
                return ResultUtil.fail(400, "未导入数据");
            }
        }
        return ResultUtil.fail(400, "传入错误");
    }
}
