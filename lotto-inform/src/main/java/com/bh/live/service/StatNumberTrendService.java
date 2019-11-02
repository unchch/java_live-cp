package com.bh.live.service;


import java.util.Map;


public interface StatNumberTrendService {

    Map<String,Object> queryNumberTrend(int position, int count, Integer variety);
}
