package com.bh.live.service.impl;

import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.StatHotColdAnalysisDao;
import com.bh.live.pojo.res.inform.NumberTrendDataRes;
import com.bh.live.service.StatHotColdAnalysisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 冷热分析
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/15 12:24
 */
@Service
public class StatHotColdAnalysisServiceImpl implements StatHotColdAnalysisService {

    @Resource
    private StatHotColdAnalysisDao hotColdAnalysisDao;

    @Override
    public Map<String, Object> queryHotColdAnalysis( Integer variety) {
        Map<String, Object> resultMap = new HashMap<>();
        List<List<NumberTrendDataRes>> numberList = new ArrayList<>();
        List<NumberTrendDataRes> list = null;
        /**
         *  冷码：出现次数为0
         *  热码：出现次数大于等于4
         *  温码：出现次数大于0小于4
         */
        int end=0;
        switch (variety){
            //幸运飞艇// pk10
            case 0:
            case 1:
                end=10;
                break;
            // 重庆时时彩//新疆时时彩//天津时时彩//广东11选5
            case 2:
            case 7:
            case 10:
            case 9:
                end=5;
                break;
        }
        for (int i = 1; i <= end; i++) {
            List<NumberTrendDataRes> hotColdAnalysis = hotColdAnalysisDao.queryHotColdAnalysis(FormStatUtil.getNumberStr(i),variety);
            numberList.add(hotColdAnalysis);
        }
        resultMap.put("hotColdAnalysis",numberList);
        return resultMap;
    }
}
