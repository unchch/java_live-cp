package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.HistoryStatisticsService;
import com.bh.live.service.StatHistoryStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 历史统计
 *
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/27 18:16
 */
@RestController
@Slf4j
public class HistoricalStatisticalController {
    @Autowired
    private StatHistoryStatisticsService statHistoryStatisticsService;
    @Autowired
    private HistoryStatisticsService statisicsService;

    /**
     * 历史统计--- 综合
     *
     * @param varietyType
     * @return
     */
    @GetMapping(value = "/history/statistics/pageOne")
    public Result page(Integer varietyType) {
        return Result.success(statisicsService.pageOne(varietyType));
    }

    /**
     * 历史統計 单双大小
     *
     * @param
     * @return
     */
    @GetMapping(value = "/history/statistics/pageTwo")
    public Result todayStatistics(Integer varietyType) {
        return Result.success(statisicsService.pageTwo(varietyType));
    }

    /**
     * 香港彩 热门统计
     *
     * @param
     * @return
     */
    @RequestMapping(value = "history/statistics/hongKongStatistics", method = RequestMethod.GET)
    @ResponseBody
    public Result hongKongStatistics(Integer expect, Integer type) {
        return Result.success(statisicsService.statistics(expect, type));
    }
}
