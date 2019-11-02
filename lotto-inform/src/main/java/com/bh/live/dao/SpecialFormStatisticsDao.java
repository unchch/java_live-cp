package com.bh.live.dao;

import com.bh.live.pojo.res.inform.SpecialFormStatisticsRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 特殊形态统计
 *
 * @author Administrator
 * @title: ISpecialFormStatisticsDao
 * @projectName live
 * @description: TODO
 * @date 2019/6/19  20:13
 */
@Mapper
public interface SpecialFormStatisticsDao {
    /**
     * 获取不同时期位置上的号码列表
     *
     * @param param
     * @return
     */
    List<SpecialFormStatisticsRes> getSpecialFormList(@Param("param") Map<String, Object> param);

}
