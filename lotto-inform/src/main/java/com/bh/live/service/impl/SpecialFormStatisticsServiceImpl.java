package com.bh.live.service.impl;

import com.bh.live.common.utils.FormSpecialUtil;
import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.SpecialFormStatisticsDao;
import com.bh.live.pojo.res.inform.SpecialFormStatisticsRes;
import com.bh.live.service.SpecialFormStatisticsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: SpecialFormStatisticsServiceImpl
 * @projectName live
 * @description: TODO
 * @date 2019/6/19  20:16
 */
@Service
public class SpecialFormStatisticsServiceImpl implements SpecialFormStatisticsService {

    @Resource
    private SpecialFormStatisticsDao specialFormStatisticsDao;

    /**
     * 获取不同时期位置上的号码列表
     *
     * @param timeDate
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> getSpecialFormList(String timeDate, Integer type, Integer varietyType) {
        Map<String, Object> param = Maps.newHashMap();
        Map<String, Integer> map =  Maps.newHashMap();
        String abStr ="ab-";
        String abbStr ="abb-";
        String aabbStr ="aabb-";
        String bigSmallStr ="bigSmall:";
        String singleDoubleStr="singleDouble:";
        String dragonTigerStr ="dragonTiger";
        param.put("type", type);
        param.put("timeDate", timeDate);
        param.put("varietyType", varietyType);
        param.put("number", FormStatUtil.getNumberStr(type));
        param.put("dragonTiger",FormStatUtil.getNumberStrDragonTiger(type));
        List<SpecialFormStatisticsRes> specialFormList = specialFormStatisticsDao.getSpecialFormList(param);
        //初始值 ，下标
        String[] abFrom = {specialFormList.get(0).getBigSmall(),"0",specialFormList.get(0).getSingleDouble(),"0",specialFormList.get(0).getDragonTiger(),"0"};
        String[] abbFrom = {specialFormList.get(0).getBigSmall(),"0",specialFormList.get(0).getSingleDouble(),"0",specialFormList.get(0).getDragonTiger(),"0"};
        //为了保证数据正确性，sql多查3条数据
        for (int i = 1; i < specialFormList.size()-3; i++) {
            //ab形态大小
            abFrom(map, abStr, bigSmallStr, specialFormList.get(i).getBigSmall(), abFrom,0,1, i);
            //ab形态单双
            abFrom(map, abStr, singleDoubleStr, specialFormList.get(i).getSingleDouble(), abFrom,2,3, i);
            //ab龙虎
            if (specialFormList.get(i).getDragonTiger()!=null){
                abFrom(map, abStr, dragonTigerStr, specialFormList.get(i).getDragonTiger(), abFrom,4,5, i);
                abFrom[4]=specialFormList.get(i).getDragonTiger();
            }
            abFrom[0]=specialFormList.get(i).getBigSmall();
            abFrom[2]=specialFormList.get(i).getSingleDouble();


            //abb形态大小
            abbForm(map, abbStr, bigSmallStr, specialFormList, abbFrom,0,1, i);
        }
        return null;
    }

    private void abbForm(Map<String, Integer> map, String abbStrStr, String bigSmallStr, List<SpecialFormStatisticsRes> specialFormStatisticsRes, String[] abbFrom, int i, int i1, int i2) {
        if(abbFrom[i].equals(specialFormStatisticsRes.get(i2).getSingleDouble())){
            if(!abbFrom[i].equals(specialFormStatisticsRes.get(i2+1).getSingleDouble())){

            }
        }

    }


    /**
     * ab形态设置
     * @param map 结果集
     * @param abStr map的key
     * @param bigSmallStr 比较
     * @param param 比较
     * @param abFrom 初始值
     * @param initialStr 下标
     * @param initialCount 下标
     * @param i list下标
     */
    private void abFrom(Map<String, Integer> map, String abStr, String bigSmallStr, String param, String[] abFrom ,int initialStr ,int initialCount, int i) {
        if(abFrom[initialStr].equals(param)){
            int expect = i-Integer.parseInt(abFrom[initialCount]);
            abFrom[initialCount] = i + "";
            if (expect>4){
                map.put(abStr+bigSmallStr+expect,map.get(abStr+bigSmallStr+expect)==null ? 1:map.get(abStr+bigSmallStr+expect)+1);
            }
        }
    }


}
