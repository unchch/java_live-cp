package com.bh.live.dao;

import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.pojo.res.inform.OpenStatisticsCodeRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatHistoryDrawDao {

    /**
     * 历史开奖数据统计
     * @param date
     * @param varietyType 彩種類型
     * @return
     */
    List<StatHistoryDraw> queryStatHistoryDraw(@Param("date") String date, @Param("varietyType") Integer varietyType);

    /**
     * 历史开奖数据统计
     * @param varietyType
     * @return
     */
    List<StatHistoryDraw> queryStatHistoryDrawByCondition(@Param("varietyType") Integer varietyType);

    /**
     *
     * @param expect
     * @return
     */
    List<String> queryStatHistoryDrawExpect(@Param("expect") Integer expect, @Param("varietyType") Integer varietyType);

    /**
     * 历史开奖数据统计
     * @return
     */
    @Select(" SELECT * FROM t_variety_current where DATE_FORMAT(open_time,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') AND variety_type =#{varietyType} ORDER BY open_time asc")
    List<StatHistoryDraw> queryStatHistoryDrawASC(@Param("varietyType") Integer varietyType);

    /**
     * 今日雙面統計
     * @param param
     * @return
     */
    List<Map<String,Integer>> queryTodayStatistics(@Param("param") Map<String, Object> param);


    /**
     * 時時彩
     * @param varietyType   时时彩类型
     * @return
     */
    List<Map<String,Integer>> querySscStatistics(@Param("varietyType") Integer varietyType);


    /**
     *  最近2期作爲一列結果
     * @param varietyType
     * @return
     */
    List<OpenStatisticsCodeRes> queryExpect(@Param("varietyType") Integer varietyType);


    /**
     * 香港彩
     * @param param
     * @return
     */
    List<StatHistoryDraw> queryHkHistoryDraw(@Param("param") Map<String, Object> param);


}
