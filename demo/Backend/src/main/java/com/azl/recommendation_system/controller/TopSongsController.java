package com.azl.recommendation_system.controller;

import com.azl.recommendation_system.service.ITopSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopSongsController {
    @Autowired
    private ITopSongsService iTopSongsService;
}
