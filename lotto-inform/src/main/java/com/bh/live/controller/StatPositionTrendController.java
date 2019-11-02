package com.bh.live.controller;

import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.common.result.Result;
import com.bh.live.service.StatPositionTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 位置走势
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/17 11:37
 */
@RestController
@RequestMapping("/PositionTrend/")
public class StatPositionTrendController {

    @Autowired
    StatPositionTrendService positionTrendService;

    @Autowired
    private RedisManager redisManager;

    @GetMapping("page")
    public Result getPage(int position, int count, int variety) {
        Map<String, Object> map = positionTrendService.queryPostionTrend(position, count, variety);
        return Result.success(map);
    }

}
