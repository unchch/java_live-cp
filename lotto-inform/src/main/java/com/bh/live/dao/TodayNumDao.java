package com.bh.live.dao;

import com.bh.live.model.inform.StatHistoryDraw;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  今日号码
 *
 * @date 2019/6/13
 */
@Mapper
public interface TodayNumDao {
    /**
     * 今日号码列表
     * @param varietyType 彩种类型
     * @return
     */
    List<StatHistoryDraw> queryTodayNum(@Param("varietyType") Integer varietyType);
}
