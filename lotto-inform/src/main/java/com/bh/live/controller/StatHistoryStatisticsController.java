package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.StatHistoryStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历史统计
 * @author Administrator
 * @title: StatHistoryStatisticsController
 * @projectName live
 * @description: TODO
 * @date 2019/6/12  18:37
 */
@RestController
@RequestMapping("/pk10/historyStatistics")
@Slf4j
public class StatHistoryStatisticsController {

    @Autowired
    private StatHistoryStatisticsService statHistoryStatisticsService;

    /**
     * 0:幸运飞艇  1：北京赛车
     * 历史开奖数据统计(单双大小)
     *
     * @param param
     * @return
     */
    @GetMapping("/page")
    public Result getPage(@RequestParam Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<>();
        }
        Object historyStatisticsList = statHistoryStatisticsService.getHistoryStatisticsList(param);
        return Result.success(historyStatisticsList);
    }

    /**
     * 历史统计冠亚和
     *
     * @return
     */
    @GetMapping("/crownSubCount")
    public Result getCrownSubCount(@RequestParam Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<>();
        }
        Map<String, Object> crownSubCount = statHistoryStatisticsService.getCrownSubCount(param);
        return Result.success(crownSubCount);
    }

    /**
     * 龙虎
     * @return
     */
    @GetMapping("/dragonTiger")
    public Result getDragonTigerList(@RequestParam Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<>();
        }
        Map<String, Object> dragonTigerList = statHistoryStatisticsService.getDragonTigerList(param);
        return Result.success(dragonTigerList);
    }

    /**
     * 香港彩特码龙虎
     * @return
     */
    @GetMapping("/hkDragonTiger")
    public Result hkDragonTiger() {
        List<Map<String, Integer>> dragonTigerList = statHistoryStatisticsService.queryHongKongDragonTiger();
        return Result.success(dragonTigerList);
    }
}
