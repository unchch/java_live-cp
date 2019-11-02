package com.bh.live.service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: IStatHistoryStatisticsService
 * @projectName live
 * @description: TODO
 * @date 2019/6/12  18:02
 */
public interface StatHistoryStatisticsService {
    /**
     * 历史开奖数据统计(单双大小)
     *
     * @param param
     * @return
     */
    Object getHistoryStatisticsList(Map<String, Object> param);

    /**
     * 历史开奖记录数据统计(冠亚和统计)
     *
     * @param param
     * @return
     */
    Map<String, Object> getCrownSubCount(Map<String, Object> param);

    /**
     * 历史开奖记录数据统计(龙虎统计)
     *
     * @param param
     * @return
     */
    Map<String, Object> getDragonTigerList(Map<String, Object> param);

    /**
     * 香港彩特码龍虎
     * @return
     */
    List<Map<String,Integer>> queryHongKongDragonTiger();
}
