package com.bh.live.service;



import com.bh.live.model.inform.ZodiacFivePhases;

import java.util.List;
import java.util.Map;


public interface ZodiacFivePhasesService {

    Map<String,Object> queryZodiacFivePhases(int year);

    /**
     获取指定期数的结果对象
     * @return
     */
    List<ZodiacFivePhases> queryZodiac();
}
