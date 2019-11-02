package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.SeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 彩种信息类
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/2 10:42
 */
@RestController
@RequestMapping("/seed")
@Slf4j
public class SeedController {
    @Autowired
    private SeedService seedService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Result top(){
        return Result.success(seedService.selectSeed());
    }
}
