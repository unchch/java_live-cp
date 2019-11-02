package com.bh.live.dao;

import com.bh.live.model.inform.Omission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface NumberOmitDao {

    /**
     * 当前遗漏
     *
     * @return
     */
    List<Omission> currentOmission(@Param("varietyType") int varietyType);

    @Select("SELECT expect,crown_sub FROM t_variety_current WHERE variety_type = #{varietyType} ORDER BY open_time DESC LIMIT 1")
    Omission getTopOne(@Param("varietyType") int varietyType);
    /**
     * 获取冠亚和今日出现次数
     *
     * @param varietyType
     * @return
     */
    List<Omission> getAppearCount(@Param("varietyType") int varietyType, @Param("dateType") String dateType);

    /**
     * 获取今日遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getDateOmission(@Param("varietyType") int varietyType, @Param("sql") String sql, @Param("crownSub") int crownSub);

    /**
     * 获取当日、本周、本月出现次数小于2的遗漏情况
     *
     * @param varietyType
     * @param crownSub
     * @return
     */
    List<Omission> getMaxCrownSub(@Param("varietyType") int varietyType, @Param("crownSub") int crownSub);

    /**
     * 历史遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> historyOmission(@Param("varietyType") int varietyType);
}
