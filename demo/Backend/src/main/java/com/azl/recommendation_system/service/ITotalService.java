package com.azl.recommendation_system.service;

import com.azl.recommendation_system.pojo.*;
import com.azl.recommendation_system.util.result.Result;

public interface ITotalService {
    //统计总数
    Result gettotal(Total total);
}
