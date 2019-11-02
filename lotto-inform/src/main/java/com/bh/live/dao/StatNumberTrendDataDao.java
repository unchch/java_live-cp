package com.bh.live.dao;


import com.bh.live.pojo.res.inform.NumberTrendDataRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatNumberTrendDataDao {

    /**
     单双大小走势
     横版走势 : 位置走势
     * @param param
     * @return
     */
    List<NumberTrendDataRes> queryStatNumberTrendData(@Param("param") Map<String, Object> param);

    /**
     * 横版走势 :号码走势
     * @return
     */
    List<NumberTrendDataRes> queryCrownSubTrendData(@Param("variety") Integer variety);

    /**
     * 横版走势 :冠亚和走势
     * @param param
     * @return
     */
    List<NumberTrendDataRes> queryNumberTrendData(@Param("param") Map<String, Object> param);


}
