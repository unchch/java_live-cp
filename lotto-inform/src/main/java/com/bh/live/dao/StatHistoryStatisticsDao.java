package com.bh.live.dao;

import com.bh.live.pojo.res.inform.StatHistoryStatisticsRes;
import com.bh.live.pojo.res.inform.StatStatisticsCrownCountRes;
import com.bh.live.pojo.res.inform.StatStatisticsDragonTigerRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 历史统计
 *
 * @author Administrator
 * @title: IStatHistoryStatisticsDao
 * @projectName live
 * @description: TODO
 * @date 2019/6/12  17:42
 */
@Mapper
public interface StatHistoryStatisticsDao {
    /**
     * 历史开奖数据统计(单双大小)
     *
     * @param param
     * @return
     */
    List<StatHistoryStatisticsRes> getHistoryStatisticsList(@Param("param") Map<String, Object> param);

    /**
     * 历史开奖记录数据统计(冠亚和统计)
     *
     * @param param
     * @return
     */
    List<StatStatisticsCrownCountRes> getCrownSubCount(@Param("param") Map<String, Object> param);

    /**
     * 历史开奖记录数据统计(龙虎统计)
     *
     * @param param
     * @return
     */
    List<StatStatisticsDragonTigerRes> getDragonTigerList(@Param("param") Map<String, Object> param);

    /**
     * 时时彩历史统计(綜合)
     * @param varietyType
     * @return
     */
    List<Map<String, BigDecimal>> statHistoryZh(@Param("varietyType") Integer varietyType);

    /**
     * 时时彩历史统计(单双)
     * @param varietyType
     * @return
     */
    List<Map<String,Integer>> statHistorySingleDouble(@Param("varietyType") Integer varietyType);


    /**
     * 幸运农场，广东快了十分 （20个号码）
     * @param varietyType
     * @return
     */
    List<Map<String,BigDecimal>> statHistoryNumZh20(@Param("varietyType") Integer varietyType);

    /**
     *  幸运农场，广东快了十分（20个号码）
     * @param varietyType
     * @return
     */
    List<Map<String,Integer>> statHistory20SingleDouble(@Param("varietyType") Integer varietyType);

    /**
     * 查询特殊号
     * @param (expect 查询期数  ball 球号)
     * @return
     */
    List<Map<Object,Integer>> querySpecialCode(@Param("expect") Integer expect, @Param("ball") String ball, @Param("type") Integer type);

    /**
     * 香港彩
     * @param expect
     * @return
     */
    @Select(" SELECT open_code from t_variety_current where variety_type =11 order by open_time desc limit #{expect}")
    List<String> queryHongKong(@Param("expect") Integer expect);

    /**
     * 香港彩特码龍虎
     * @return
     */
    List<Map<String,Integer>> queryHongKongDragonTiger();
}
