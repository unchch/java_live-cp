package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.SpecialFormStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * 特殊形态统计
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/29 14:45
 */
public class SpecialFormStatisticsController {
    @Autowired
    private SpecialFormStatisticsService specialFormStatisticsService;

    @GetMapping("/cv/specialForm/list")
    public Result getSpecialFormList(String timeDate, Integer type, Integer varietyType){
        Map<String, Object> map = specialFormStatisticsService.getSpecialFormList(timeDate,  type,  varietyType);
        return  Result.success(map);
    }
}
