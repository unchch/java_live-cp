package com.bh.live.service;


import com.bh.live.model.inform.StatHistoryDraw;

import java.util.List;

public interface SelfSelectionTrendServer {
    /**
     * 自选走势
     * @return
     */
    List<StatHistoryDraw> querySelfSelectionTrend(Integer expect, Integer variety);
}
