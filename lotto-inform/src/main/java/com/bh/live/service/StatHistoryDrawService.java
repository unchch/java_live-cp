package com.bh.live.service;


import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.Map;

public interface StatHistoryDrawService {

    /**
     * 历史开奖统计(包括：長龍連開提醒)
     * @param date
     * @param date
     * @return
     */
    Map<String,Object> queryStatHistoryDraw(String date, int varietyType);

    /**
     * 今日雙面統計
     * @param param
     * @return
     */
    List<Map<String,Integer>> queryToday(Map<String, Object> param);



    /*****************************************************************************
     *****************************************************************************
     * 時時彩(今日號碼統計)
     * @return
     */
    Map<String,Integer> querySscStatistics(Integer varietyType);

    /**
     * 時時彩历史开奖(列表)
     * @return
     */
    Map<String, Object> page(@Param("param") Map<String, Object> param);

    /**
     * 雙號提醒
     * @param varietyType
     * @return
     */
    Map<String, Object> longDragon(@Param("varietyType") Integer varietyType);


}
