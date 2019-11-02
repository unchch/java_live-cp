package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.StatOpenStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 開號統計
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/11 18:09
 */
@RestController
@RequestMapping("/OpenStatistics/")
public class StatOpenStatisticsController {

    @Autowired
    private StatOpenStatisticsService openStatisticsService;


    @GetMapping("page")
    public Result getPage(Integer count, Integer variety){
            Map<String, Object> map = openStatisticsService.queryOpenStatistics(count,variety);
            return Result.success(map);
    }


    @GetMapping("getHistroy")
    public Result getHistroy(Integer count,Integer variety){
        if(count==null){
            count=0;
        }
        Map<String, Object> map = openStatisticsService.queryHistroy(count,variety);
        return Result.success(map);
    }



}
