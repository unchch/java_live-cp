package com.bh.live.dao;


import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.pojo.res.inform.OmitRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatValueInvestDao {

    String queryValueInvest(@Param("param") Map<String, Object> param);

    String queryOptionalCount(@Param("param") Map<String, Object> param);

    List<OmitRes> queryOmit(@Param("param") Map<String, Object> param);
    /**
     获取指定期数的结果对象
     * @param count 期数
     * @return
     */
    List<ColorVarietyModelRes> queryResult(@Param("count") int count, @Param("variety") Integer variety);

}
