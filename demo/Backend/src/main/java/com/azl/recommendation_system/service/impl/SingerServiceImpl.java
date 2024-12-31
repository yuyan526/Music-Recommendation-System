package com.azl.recommendation_system.service.impl;

import com.azl.recommendation_system.dao.SingerMapper;
import com.azl.recommendation_system.service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SingerServiceImpl implements ISingerService {
    @Autowired
    SingerMapper singerMapper;
    @Override
    public String queryUidByName(String name) {
        return singerMapper.queryByName(name);
    }
}
