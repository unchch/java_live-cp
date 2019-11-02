package com.bh.live.service;


import java.util.Map;


public interface StatOpenStatisticsService {

    Map<String,Object> queryOpenStatistics(Integer count, Integer variety);

    Map<String,Object> queryHistroy(Integer count, Integer variety);
}
