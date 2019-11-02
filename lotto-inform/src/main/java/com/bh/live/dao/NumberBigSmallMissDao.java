package com.bh.live.dao;

import com.bh.live.model.inform.Omission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @title: INumberMissingDao
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/25  16:27
 */
@Mapper
public interface NumberBigSmallMissDao {

    /**
     * 当前遗漏
     *
     * @return
     */
    List<Omission> currentOmission(@Param("varietyType") int varietyType, @Param("fieldCondition") String fieldCondition);

    /**
     * 获取冠亚和今日出现次数
     *
     * @param varietyType
     * @return
     */
    List<Omission> getAppearCount(@Param("varietyType") int varietyType, @Param("number") int number,
                                  @Param("dateType") String dateType, @Param("field") String field);

    /**
     * 获取今日遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getDateOmission(@Param("varietyType") int varietyType, @Param("sql") String sql, @Param("number") Object number);

    /**
     * 获取当日、本周、本月出现次数小于2的遗漏情况
     *
     * @param varietyType
     * @return
     */
    List<Omission> getMaxCrownSub(@Param("varietyType") int varietyType, @Param("number") int number, @Param("type") String type);

    /**
     * 历史遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> historyOmission(@Param("varietyType") int varietyType, @Param("number") int number, @Param("type") String type);

    @Select("SELECT expect,${field} numberBall FROM t_variety_current WHERE variety_type = #{varietyType} ORDER BY open_time DESC LIMIT 1")
    Omission getTopOne(@Param("varietyType") int varietyType, @Param("field") String field);
}
