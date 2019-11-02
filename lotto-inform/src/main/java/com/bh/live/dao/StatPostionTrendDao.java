package com.bh.live.dao;


import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatPostionTrendDao {

    /**
     * 根据期号和排名和球号获取结果对象
     * @param param param.put("ball", 1); 1表示冠军 以此类推
     *              param.put("number", "number_one_ball");
     *              param.put("expect", "735005");
     * @return
     */
    String getNextResult(@Param("param") Map<String, Object> param);

    /**
     获取指定期数的结果对象
     * @param count 期数
     * @param endCount 结束坐标
     * @return
     */
    List<ColorVarietyModelRes> queryPk10Rsult(@Param("count") int count, @Param("endCount") Integer endCount, @Param("variety") Integer variety);

    /**
     获取指定期数和位置的结果对象
     * @param param
     * @return
     */
    List<ColorVarietyModelRes> queryPk10BallRsult(@Param("param") Map<String, Object> param);

    /**
     * 获取当天期数
     * @return
     */
    Integer queryOpenCount(@Param("variety") Integer variety);


}
