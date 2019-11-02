package com.bh.live.dao;

import com.bh.live.pojo.res.inform.AfcAndTrendRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface AfcAndTrendDao {

    /**
     * 亚冠和走势
     * @return
     */
    AfcAndTrendRes queryAfcAndTrendTop(@Param("expect") Integer expect, @Param("varietyType") Integer varietyType);
}
