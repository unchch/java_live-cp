package com.bh.live.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 时时彩历史统计
 */
public interface HistoryStatisticsService {

    /**
     * 時時彩  ---- 综合统计
     * @return
     */
    List<Map<String, BigDecimal>> pageOne(Integer varietyType);

    /**
     * 時時彩-- 单双大小统计
     * @return
     */
    List<Map<String, Integer>> pageTwo(Integer varietyType);

    /**
     * 香港彩 统计
     * @param
     * @return
     */
    Map<String,Object> statistics(Integer expect, Integer type);

}
