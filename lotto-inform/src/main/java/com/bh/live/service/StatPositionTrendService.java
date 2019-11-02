package com.bh.live.service;


import java.util.Map;


public interface StatPositionTrendService {

    Map<String,Object> queryPostionTrend(int position, int count, Integer variety);
}
