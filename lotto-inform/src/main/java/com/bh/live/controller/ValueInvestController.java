package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.pojo.res.inform.ValueInvestRep;
import com.bh.live.service.StatValueInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 投资价值
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/19 17:51
 */
@RestController
public class ValueInvestController {
    @Autowired
    private StatValueInvestService valueInvestService;

    /**
     * 获取集合数据
     *
     * @param type 类型 0表示全部 单双，大小，龙虎，任选1，任选2，任选3，任选4，任选5，任选6，任选7，任选8，
     * @param position 0表示全部 排名位置 1~10 第一名到第十名 11：冠亚和
     * @param count  期数 100期 200期 500期 1000期
     * @return
     */
    @GetMapping("/ValueInvest/page")
    public Result getPage(int type, int position, int count, int variety){
        System.out.println(" ======= 开始投资价值报表 ===== ");
        Long starttime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<ValueInvestRep> all=new ArrayList<>();
        List<ValueInvestRep> resultList=new ArrayList<>();
        // 获得全部
        if (type==0){
            if (position==0){
                List<ValueInvestRep> valueInvestList=null;
                // 初始化所有类型的全部
                for (int i=1;i<=11;i++){
                    valueInvestList = valueInvestService.queryValueInvest(i, position, count,variety);
                    if (i>5){
                        all.addAll(valueInvestList);
                    }
                    map.put("valueInvestList", valueInvestList);
                    /* redisManager.setByFastJson(RedisKey.valueInvestRedisKey(i,position,count,variety),map);*/
                }
                // 随机获取 集合中的对象
                int[] arr = CommonUtil.randomArray(0, all.size()-1, 50);
                for (int i=0;i<arr.length;i++){
                    ValueInvestRep dto = all.get(arr[i]);
                    resultList.add(dto);
                }
                map.put("valueInvestList",resultList);
                /*redisManager.setByFastJson(RedisKey.valueInvestRedisKey(type,position,count,variety),map);*/
                System.out.println(" ======= 投资价值结束 ===== 耗时 ：{}" + (System.currentTimeMillis() - starttime));
                return Result.success(map);
            }else {
                List<ValueInvestRep> valueInvestList=null;
                if (position==11){
                    for (int i = 1; i <=2 ; i++) {
                        valueInvestList = valueInvestService.queryValueInvest(i, position, count,variety);
                        all.addAll(valueInvestList);
                    }
                    map.put("valueInvestList",all);
                }else{
                    for (int i = 5; i <=11 ; i++) {
                        valueInvestList = valueInvestService.queryValueInvest(i, position, count,variety);
                        all.addAll(valueInvestList);
                    }
                    // 随机获取 集合中的对象
                    int[] arr = CommonUtil.randomArray(0, all.size()-1, 50);
                    for (int i=0;i<arr.length;i++){
                        ValueInvestRep dto = all.get(arr[i]);
                        resultList.add(dto);
                    }
                    map.put("valueInvestList",resultList);
                }
                System.out.println(" ======= 投资价值结束 ===== 耗时 ：{}" + (System.currentTimeMillis() - starttime));
                return Result.success(map);
            }
        }else {
            map.put("valueInvestList", valueInvestService.queryValueInvest(type, position, count,variety));
            System.out.println(" ======= 投资价值结束 ===== 耗时 ：{}" + (System.currentTimeMillis() - starttime));
            /* redisManager.setByFastJson(RedisKey.valueInvestRedisKey(type,position,count,variety),map);*/
            return Result.success(map);
        }
    }
}
