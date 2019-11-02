package com.bh.live.dao;

import com.bh.live.model.inform.Omission;
import com.bh.live.pojo.res.inform.DragonRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 每日长龙
 *
 * @author Administrator
 * @title: IDayLongDao
 * @projectName live
 * @description: TODO
 * @date 2019/6/13  16:44
 */
@Mapper
public interface DayLongDao {
    /**
     * 获取所有所需数据
     *
     * @param param
     * @return
     */
    List<DragonRes> getStatDayLongList(@Param("param") Map<String, Object> param);

    /**
     * 根据条件获取最大期数
     *
     * @param param
     * @return
     */
    Integer getMaxLdPeriod(@Param("param") Map<String, Object> param);

    
	List<Omission> getStatDayLongList(@Param("varietyType") String varietyType, @Param("sql") String sql);
}
