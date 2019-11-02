package com.bh.live.service.impl;

import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.StatNumberTrendDataDao;
import com.bh.live.pojo.res.inform.NumberTrendDataRes;
import com.bh.live.service.StatNumberTrendDateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 走势图
 * @Author: Dingo
 * @Version: 1.0
 * @date: 2019/6/15 12:24
 */
@Service
public class StatNumberTrendDateServiceImpl implements StatNumberTrendDateService {

    @Resource
    private StatNumberTrendDataDao numberTrendDataDao;

    @Override
    public Map<String, Object> queryNumberTrendDate(Integer type,Integer num,Integer Variety) {
        Map<String, Object> param = new HashMap<>();
        param.put("variety",Variety);
        Map<String, Object> resultMap = new HashMap<>();
        List<NumberTrendDataRes> numberTrendDataDtos =null;
        //横版走势 : 单双大小走势/位置走势：1 ， 号码走势：2 ，冠亚和走势：3
        if (type==1){
            param.put("number", FormStatUtil.getNumberStr(num));
            numberTrendDataDtos = numberTrendDataDao.queryStatNumberTrendData(param);
        }else if (type==2){
            param.put("number",num);
            numberTrendDataDtos=numberTrendDataDao.queryNumberTrendData(param);
        }else if (type==3){
            numberTrendDataDtos=numberTrendDataDao.queryCrownSubTrendData(Variety);
        }
        if (!CollectionUtils.isEmpty(numberTrendDataDtos)){
            Collections.reverse(numberTrendDataDtos);
        }
        resultMap.put("numberTrendData",numberTrendDataDtos);
        return resultMap;
    }
}
