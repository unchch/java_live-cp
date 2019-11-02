package com.bh.live.controller;

import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.common.result.Result;
import com.bh.live.service.AfcAndTrendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 亚冠和走势/总和走势 控制器
 */
@RestController
@RequestMapping("/AFC/")
@Slf4j
public class AfcAndTrendController {

    @Autowired
    private AfcAndTrendService afcAndTrendServer;

    @Autowired
    private RedisManager redisManager;

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @ResponseBody
    public Result page(Integer expect, int variety) {
        Map map = afcAndTrendServer.queryAfcAndTrendPage(expect, variety);
        return Result.success(map);

    }
}
