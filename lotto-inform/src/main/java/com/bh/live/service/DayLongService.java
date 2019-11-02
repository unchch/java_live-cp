package com.bh.live.service;

import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: IDayLongService
 * @projectName live
 * @description: TODO
 * @date 2019/6/13  16:49
 */
public interface DayLongService {
    /**
     * 获取所有所需数据
     *
     * @param param
     * @return
     */
    List<Map<String,Object>> getStatDayLongList(@Param("param") Map<String, Object> param);

    List<Object> getStatDayLongList(String varietyType, String ball, String type);
}
