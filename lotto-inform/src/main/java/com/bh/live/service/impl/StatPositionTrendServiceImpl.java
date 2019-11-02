package com.bh.live.service.impl;

import com.bh.live.common.TrendUtils.CalcHBDSDX;
import com.bh.live.dao.StatPostionTrendDao;
import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.service.StatPositionTrendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 位置走势
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/17 12:24
 */
@Service
public class StatPositionTrendServiceImpl implements StatPositionTrendService {

    @Resource
    private StatPostionTrendDao postionTrendDao;

    /**
     * 查找指定排名的位置走势
     *
     * @param position 位置
     * @param count    期数
     * @return
     */
    @Override
    public Map<String, Object> queryPostionTrend(int position, int count,Integer variety) {
        System.out.println(" ======= 开始位置走势报表 ===== ");
        Long starttime = System.currentTimeMillis();
        if (count==0){
            // 获取当天期号
            count = postionTrendDao.queryOpenCount(variety);
        }
        Map<String, Object> resultMap = new HashMap<>();
        //整合所有期数对象
        List<ColorVarietyModelRes> resultList = new ArrayList<>();
        // 获取指定期数的结果对象
        List<ColorVarietyModelRes> resultCountVos = postionTrendDao.queryPk10Rsult(count, null,variety);
        resultMap.put("expectList",com.bh.live.common.TrendUtils.CalcOmit.getExpectList(resultCountVos));
        resultList.addAll(resultCountVos);
        int start=0,end=0;
        switch (variety){
            // pk10 幸运飞艇
            case  0:
            case 1:
                start=1;
                end=10;
                break;
            // 重庆时时彩//圣地彩//新疆时时彩//天津时时彩
            case 2:
            case 3:
            case 7:
            case 10:
                start=0;
                end=9;
                break;
                //幸运农场//广东快乐十分//广东十一选五
            case 5:
            case 8:
                start=1;
                end=20;
                break;
            case 9:
                start=1;
                end=11;
                break;
        }
        // 查询最大遗漏
        Map<String, Object> CalcOmit = com.bh.live.common.TrendUtils.CalcOmit.calcOmit(count, position, resultCountVos,postionTrendDao,start,end,variety);
        // 获得最大遗漏
        int maxOmit = (Integer) CalcOmit.get("maxNum");
        System.out.println("总最大遗漏是：" + maxOmit);
        // 获取最大遗漏重新去查询数据
        resultCountVos = postionTrendDao.queryPk10Rsult(count, maxOmit,variety);
        CalcOmit.remove("maxNum");
        resultList.addAll(resultCountVos);
        // 反向，正向，重号，单，双，大，小
        Map<String, Object> hb_ds_dx_all = CalcHBDSDX.getHB_DS_DX(position, resultList,count,1,variety);
        resultMap.put("CalcOmit", CalcOmit);
        resultMap.put("hb_ds_dx_all", hb_ds_dx_all);
        System.out.println(" ======= 开号位置走势结束 ===== 耗时 ：{}" + (System.currentTimeMillis() - starttime));
        return resultMap;
    }


}
