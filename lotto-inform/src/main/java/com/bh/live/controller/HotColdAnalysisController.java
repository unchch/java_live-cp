package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.StatHotColdAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 冷热分析
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/15 17:29
 */
@RestController
public class HotColdAnalysisController {
    @Autowired
    private StatHotColdAnalysisService hotColdAnalysisService;
    @GetMapping("/HotColdAnalysis/page")
    public Result getPage(Integer variety){
        Map<String, Object> map = hotColdAnalysisService.queryHotColdAnalysis(variety);
        return Result.success(map);
    }
}
