package com.bh.live.controller;


import com.bh.live.common.result.Result;
import com.bh.live.service.SelfSelectionTrendServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 自选走势 控制器
 */
@RestController
@RequestMapping("/pk10/self/")
@Slf4j
public class SelfSelectionTrendController {

    @Autowired
    private SelfSelectionTrendServer selfSelectionTrendServer;

    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Result top(Integer expect, Integer variety){
        return Result.success(selfSelectionTrendServer.querySelfSelectionTrend(expect,variety));
    }
}
