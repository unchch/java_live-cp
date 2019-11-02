package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.HongKongLotteryService;
import com.bh.live.service.StatHistoryDrawService;
import com.bh.live.service.ZodiacFivePhasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/27 16:26
 */
@RestController
public class HistoryController {
    @Autowired
    private StatHistoryDrawService statHistoryDrawService;
    @Autowired
    private ZodiacFivePhasesService zodiacFivePhasesService;
    @Autowired
    private HongKongLotteryService hongkongLottery;
    
    /**
     * 列表
     *
     * @param param
     * @return
     */
    @GetMapping(value = "/ssc/history/page")
    public Result page(@RequestParam(required = true) Map<String, Object> param) {
        return Result.success(statHistoryDrawService.page(param));
    }

    /**
     * 今日號碼統計 top
     *
     * @return
     * @paramh
     */
    @GetMapping(value = "/ssc/history/todayStatistics")
    public Result todayStatistics(int varietyType) {
        return Result.success(statHistoryDrawService.querySscStatistics(varietyType));
    }

    /**
     * 长龍连开
     *
     * @param varietyType
     * @return
     */
    @GetMapping(value = "/ssc/history/longDragon")
    public Result longDragon(int varietyType) {
        return Result.success(statHistoryDrawService.longDragon(varietyType));
    }

    /**
     * 六合彩历史开奖 - 属性参照生肖、五行数据 -
     *
     * @param year
     * @return
     */
    @GetMapping("/ZodiacFivePhases/page")
    public Result getPage(int year) {
        Map<String, Object> map = zodiacFivePhasesService.queryZodiacFivePhases(year);
        return Result.success(map);
    }

}
