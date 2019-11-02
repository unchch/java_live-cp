package com.bh.live.dao;

import com.bh.live.model.inform.StatHistoryDraw;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SelfSelectionTrendDao {

    /**
     * 自选走势
     * @return
     */
    List<StatHistoryDraw> querySelfSelectionTrend(@Param("expect") Integer expect, @Param("variety") Integer variety);
}
