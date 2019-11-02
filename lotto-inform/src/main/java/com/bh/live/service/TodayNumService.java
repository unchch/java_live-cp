package com.bh.live.service;

import java.util.Map;

public interface TodayNumService {

    /**
     * 今日号码
     * @param
     * @return
     */
    Map<Integer,Map<Integer,Integer>> queryTodayNum(Integer varietyType);
}
