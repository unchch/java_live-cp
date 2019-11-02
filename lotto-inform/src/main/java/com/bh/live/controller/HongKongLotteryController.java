package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.HongKongLotteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cv/HongKong/")
@Slf4j
public class HongKongLotteryController {

    @Autowired
    private HongKongLotteryService hongkongLottery;

    /**
     * 香港彩特码遗漏
     *
     * @param total 期数
     */
    @RequestMapping(value = "temaMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result temaMissing(int total) {
        Map<String, Object> map = hongkongLottery.queryTemaMissing(total);
        return Result.success(map);
    }

    /**
     * 香港彩 两面遗漏
     */
    @RequestMapping(value = "twoDateMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result twoDateMissing() {

        Map<String, Object> map = hongkongLottery.queryTwoDateMissing();
        return Result.success(map);
    }

    /**
     * 特码尾数遗漏
     */
    @RequestMapping(value = "tmLastNumberMiss", method = RequestMethod.GET)
    @ResponseBody
    public Result tmLastMiss() {
        Map<String, Object> map = hongkongLottery.queryTmLastNumberMissing();
        return Result.success(map);
    }

    /**
     * 特码生肖周期遗漏
     */
    @RequestMapping(value = "tmZodiacCyclesMiss", method = RequestMethod.GET)
    @ResponseBody
    public Result tmZodiacCyclesMiss() {
        Map<String, Object> map = hongkongLottery.queryZodiacCyclesMiss();
        return Result.success(map);
    }

    /**
     * 特码号码段遗漏
     */
    @RequestMapping(value = "tmNumberSegmentMiss", method = RequestMethod.GET)
    @ResponseBody
    public Result tmNumberSegmentMiss() {
        Map<String, Object> map = hongkongLottery.queryNumberSegmentMiss();
        return Result.success(map);
    }

    /**
     * 香港彩正码遗漏
     *
     * @param total 期数
     */
    @RequestMapping(value = "zmaMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result zmaMissing(int total) {
        Map<String, Object> map = hongkongLottery.queryZmaMissing(total);
        return Result.success(map);
    }

    /**
     * 香港彩生肖特码遗漏
     */
    @RequestMapping(value = "zodiacTemaMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result zodiacTemaMissing() {
        Map<String, Object> map = hongkongLottery.queryZodiacTemaMissing();
        return Result.success(map);
    }

    /**
     * 香港彩生肖正码遗漏
     */
    @RequestMapping(value = "zodiacZmaMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result zodiacZmaMissing() {
        Map<String, Object> map = hongkongLottery.queryZodiacZmaMissing();
        return Result.success(map);
    }

    /**
     * 香港彩生肖遗漏
     */
    @RequestMapping(value = "zodiacMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result zodiacMissing() {
        Map<String, Object> map = hongkongLottery.queryZodiacMissing();
        return Result.success(map);
    }

    /**
     * 香港彩波色遗漏
     */
    @RequestMapping(value = "boseMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result boseMissing() {
        Map<String, Object> map = hongkongLottery.queryBoseMissing();
        return Result.success(map);
    }

    /**
     * 香港彩历史同期
     */
    @RequestMapping(value = "historicalPeriod", method = RequestMethod.GET)
    @ResponseBody
    public Result historicalPeriod() {
        Map<String, Object> map = hongkongLottery.queryHistoricalPeriod();
        return Result.success(map);
    }

    /**
     * 香港彩历史遗漏
     */
    @RequestMapping(value = "historyMissing", method = RequestMethod.GET)
    @ResponseBody
    public Result historyMissing(String date, String type) {
        Map<String, Object> map = hongkongLottery.queryHistoryMissing(date, type);
        return Result.success(map);
    }
}
