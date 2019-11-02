package com.bh.live.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 号码走位报警service
 *
 * @author Administrator
 * @title: IWalkAlarmService
 * @projectName live
 * @description: TODO
 * @date 2019/6/17  21:32
 */
public interface WalkAlarmService {
    /**
     * @param param
     * @return
     */
    List<Map<String,Object>> getWalkAlarmList(@Param("param") Map<String, Object> param);
}
