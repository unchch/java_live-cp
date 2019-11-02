package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.DayLongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 每日长龙控制层
 *
 * @author Administrator
 * @title: DayLongController
 * @projectName live
 * @description: TODO
 * @date 2019/6/17  11:26
 */
@RestController
@RequestMapping("/pk10/dayLong")
@Slf4j
public class DayLongController {
    @Autowired
    private DayLongService dayLongService;

    @GetMapping("/list")
    public Result getStatDayLongList(String varietyType, String ball, String type) {
        log.info("每日长龙统计开始: 彩种" + varietyType+type+"长龙"+" 第"+ball+"球");
        return Result.success(dayLongService.getStatDayLongList(varietyType,ball,type));

    }
}
