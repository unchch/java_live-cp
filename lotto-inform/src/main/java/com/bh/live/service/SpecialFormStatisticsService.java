package com.bh.live.service;

import java.util.Map;

/**
 * 特殊形态统计
 *
 * @author Administrator
 * @title: ISpecialFormStatisticsService
 * @projectName live
 * @description: TODO
 * @date 2019/6/19  20:15
 */
public interface SpecialFormStatisticsService {

    /**
     * 获取不同时期位置上的号码列表
     *
     * @param timeDate
     * @param type
     * @return
     */
    Map<String, Object> getSpecialFormList(String timeDate, Integer type, Integer varietyType);

}
