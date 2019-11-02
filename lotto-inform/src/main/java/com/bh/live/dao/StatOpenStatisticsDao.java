package com.bh.live.dao;


import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.pojo.res.inform.OpenStatisticsCodeRes;
import com.bh.live.pojo.res.inform.OpenStatisticsRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatOpenStatisticsDao {

    List<OpenStatisticsRes> queryOpenStatisticsLeft(@Param("param") Map<String, Object> param);

    List<OpenStatisticsCodeRes> queryOpenStatisticsRight(@Param("param") Map<String, Object> param);

    Integer getOneNumber(@Param("variety") Integer variety);

    String getOpenCode(@Param("count") Integer count, @Param("variety") Integer variety);

     List<ColorVarietyModelRes> queryHistroy(@Param("count") Integer count, @Param("variety") Integer variety);
}
