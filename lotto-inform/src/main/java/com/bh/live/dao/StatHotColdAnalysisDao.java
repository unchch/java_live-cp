package com.bh.live.dao;


import com.bh.live.pojo.res.inform.NumberTrendDataRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StatHotColdAnalysisDao {

    /**
     冷热分析
     * @param number
     * @return
     */
    List<NumberTrendDataRes> queryHotColdAnalysis(@Param("number") String number, @Param("variety") Integer variety);

}
