package com.bh.live.dao;

import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 号码走位报警
 *
 * @author Administrator
 * @title: IWalkAlarmDao
 * @projectName live
 * @description: TODO
 * @date 2019/6/17  21:30
 */
@Mapper
public interface WalkAlarmDao {
    /**
     * 获取报警的号码列表
     *
     * @param param
     * @return
     */
    List<ColorVarietyModelRes> getWalkAlarmList(@Param("param") Map<String, Object> param);
}
