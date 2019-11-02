package com.bh.live.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface DragonDao {
    /**
     *  长龍统计
     * @param param
     * @return
     */
    void insertDragon(@Param("param") Map<String, Object> param);
}
