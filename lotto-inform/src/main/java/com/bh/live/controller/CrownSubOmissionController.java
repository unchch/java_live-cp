package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.NumberOmitService;
import com.bh.live.service.TwoFaceOmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 冠亚和遗漏
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/27 17:45
 */
@RestController
@Slf4j
public class CrownSubOmissionController {
    @Resource
    private NumberOmitService omitService;
    @Resource
    private TwoFaceOmitService twoFaceOmitService;
    /**
     * 冠亚和遗漏
     *
     * @return
     */
    @GetMapping("/omission/crownSubOmission")
    public Result crownSubOmission(@RequestParam int varietyType) {

        return Result.success(omitService.getOmissionCount(varietyType));
    }
    /**
     * 冠亚和两面遗漏
     *
     * @return
     */
    @GetMapping("/omission/twoFaceOmission")
    public Result twoFaceOmission(@RequestParam int varietyType) {
        return Result.success(twoFaceOmitService.getOmissionCount(varietyType));
    }
}
