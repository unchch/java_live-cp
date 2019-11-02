package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.StatHistoryDrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 历史开奖
 */
@RestController
@RequestMapping("/pk10/history/")
@Slf4j
public class StatHistoryDrawController {

    @Autowired
    private StatHistoryDrawService statHistoryDrawService;

    /**
     * 历史开奖-表格数据
     *
     * @param date
     * @param varietyType
     * @return
     */
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Result page(String date, int varietyType){
        return Result.success(statHistoryDrawService.queryStatHistoryDraw(date,varietyType));
    }

    /**
     * 今日雙面統計
     * @param param
     * @return
     */
    @RequestMapping(value = "todayStatistics",method = RequestMethod.GET)
    @ResponseBody
    public Result todayStatistics(@RequestBody(required = false) Map<String,Object> param){
        return Result.success(statHistoryDrawService.queryToday(param));
    }


}
