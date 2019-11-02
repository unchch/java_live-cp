package com.bh.live.dao;


import com.bh.live.model.inform.ZodiacFivePhases;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ZodiacFivePhasesDao {

    /**
     获取指定期数的结果对象
     * @param year 年份
     * @return
     */
    List<ZodiacFivePhases> queryZodiacFivePhases(@Param("year") int year);


    /**
     获取指定期数的结果对象
     * @param year 年份
     * @return
     */
    @Select( " SELECT target_name targetName,num_json numJson from t_zodiac_five_phases where type_name =1  AND is_usable = 1")
    Map<String,String> queryZodiacMap(@Param("year") int year);


    /**
     波色
     * @return
     */
    @Select( " SELECT target_name targetName,num_json numJson ,particular_year particularYear from t_zodiac_five_phases where type_name =3  AND is_usable = 1")
    List<ZodiacFivePhases> queryZodiac();

    
    /**
    获取所有生肖对应数字
    * @param year 年份
    * @return
    */
   List<ZodiacFivePhases> queryZodiacNumber(@Param("year") int year);
}
