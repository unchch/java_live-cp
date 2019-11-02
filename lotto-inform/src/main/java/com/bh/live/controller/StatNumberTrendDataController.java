package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.StatNumberTrendDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 走势图
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/15 12:09
 */
@RestController
@RequestMapping("/NumberTrendData/")
public class StatNumberTrendDataController {

    @Autowired
    private StatNumberTrendDateService numberTrendDateService;

    /**
     * 获取走势图数据
     *
     * @param num  号码或者排名
     * @param type 单双大小走势 ： 1
     *             横版走势 : 位置走势：1 ， 号码走势：2 ，冠亚和走势：3
     * @return
     */
    @GetMapping("page")
    public Result getPage(Integer type, Integer num, Integer variety) {
        if (num == null) {
            num = 1;
        }
        Map<String, Object> map = numberTrendDateService.queryNumberTrendDate(type, num, variety);
        return Result.success(map);
    }

}
