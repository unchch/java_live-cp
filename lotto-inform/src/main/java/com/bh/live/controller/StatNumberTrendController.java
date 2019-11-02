package com.bh.live.controller;

import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.common.result.Result;
import com.bh.live.service.StatNumberTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 号码走势
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/26 11:37
 */
@RestController
@RequestMapping("/NumberTrend/")
public class StatNumberTrendController {

    @Autowired
    private StatNumberTrendService numberTrendService;

    @Autowired
    private RedisManager redisManager;

    @GetMapping("page")
    public Result getPage(int position, int count, Integer variety) {
        Map<String, Object> map = numberTrendService.queryNumberTrend(position, count, variety);
        return Result.success(map);
    }

}
