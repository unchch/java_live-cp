package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.service.TwoDataStatisticsService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cv/twoData/")
@Slf4j
public class TwoDataStatisticsController {

    @Autowired
    private TwoDataStatisticsService twodata;

    /**
     * 两面数据统计
     */
    @RequestMapping(value = "twoDataStatistics", method = RequestMethod.GET)
    public Result twoDataStatistics(int ball, String varietyType) {
        List<Object> map = twodata.queryTwoDataStatistics(ball, varietyType);
        return Result.success(map);
    }

    /**
     * 号码规律
     *
     * @return
     */
    @RequestMapping(value = "numberLaw", method = RequestMethod.GET)
    @ResponseBody
    public Result numberLaw(int number, int expect, String varietyType) {
        Map<String, Object> map = twodata.queryNumberLaw(number, expect, varietyType);
        return Result.success(map);


    }

    /**
     * 遗漏大数据
     *
     * @param varietyType 彩种类型
     */
    @RequestMapping(value = "missingBigData", method = RequestMethod.GET)
    @ResponseBody
    public Result missingBigData(String dateType, int index, String varietyType) {
        Map<String, Object> map = twodata.queryMissingBigData(dateType, index, varietyType);
        return Result.success(map);
    }

}
