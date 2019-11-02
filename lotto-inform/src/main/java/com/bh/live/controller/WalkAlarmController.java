package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.WalkAlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 号码走位报警控制层
 *
 * @author Administrator
 * @title: WalkAlarmController
 * @projectName live
 * @description: TODO
 * @date 2019/6/17  21:34
 */
@RestController
@RequestMapping("/pk10/walkAlarm")
@Slf4j
public class WalkAlarmController {

    @Autowired
    private WalkAlarmService walkAlarmService;

    @GetMapping("/list")
    public Result getWalkAlarmList(@RequestParam Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<>();
        }
        log.info("号码走位报警开始: " + param);
        return Result.success(walkAlarmService.getWalkAlarmList(param));

    }

}


