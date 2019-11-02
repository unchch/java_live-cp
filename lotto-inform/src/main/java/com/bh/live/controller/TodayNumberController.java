package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.TodayNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 今日号码
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/27 17:06
 */
@RestController
public class TodayNumberController {
    @Autowired
    private TodayNumService todayNumService;

    @GetMapping(value = "page")
    public Result page(Integer varietyType){
        Map map = todayNumService.queryTodayNum(varietyType);
        return Result.success(map);
    }
}
