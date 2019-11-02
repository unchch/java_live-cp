package com.bh.live.service.impl;

import com.bh.live.common.TrendUtils.CalcHBDSDX;
import com.bh.live.common.TrendUtils.CalcOmit;
import com.bh.live.dao.StatHistoryDrawDao;
import com.bh.live.dao.StatPostionTrendDao;
import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.service.AfcAndTrendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class AfcAndTrendServiceImpl implements AfcAndTrendService {
    @Resource
    private StatHistoryDrawDao statHistoryDrawDao;

    @Resource
    private StatPostionTrendDao postionTrendDao;

    /**
     * 亚冠和统计
     *
     * @param expect
     * @return
     */
    @Override
    public Map<String, Object> queryAfcAndTrendPage(Integer expect,int variety) {
        Long starttime = System.currentTimeMillis();
        if (expect==0){
            // 获取当天期号
            expect = postionTrendDao.queryOpenCount(variety);
        }
        Map<String, Object> resultMap = new HashMap<>();
        //整合所有期数对象
        List<ColorVarietyModelRes> resultList = new ArrayList<>();
        // 获取指定期数的结果对象
        List<ColorVarietyModelRes> resultCountVos = postionTrendDao.queryPk10Rsult(expect, null,variety);
        resultMap.put("expectList",statHistoryDrawDao.queryStatHistoryDrawExpect(expect,variety));
        resultList.addAll(resultCountVos);
        // 查询最大遗漏
        int end=19;
        // 快3
        if (variety==4){
            end=18;
        }
        Map<String, Object> calcOmit = CalcOmit.calcOmit(expect, 11, resultCountVos,postionTrendDao,3,end,variety);
        // 获得最大遗漏
        int maxOmit = (Integer)calcOmit.get("maxNum");
        System.out.println("总最大遗漏是：" + maxOmit);
        // 获取最大遗漏重新去查询数据
        resultCountVos = postionTrendDao.queryPk10Rsult(expect, maxOmit,variety);
        calcOmit.remove("maxNum");
        resultList.addAll(resultCountVos);
        // 反向，正向，重号，单，双，大，小
        Map<String, Object> hb_ds_dx_all = CalcHBDSDX.getHB_DS_DX(11,resultList,expect,2,variety);
        resultMap.put("CalcOmit",calcOmit);
        resultMap.put("hb_ds_dx_all", hb_ds_dx_all);
        System.out.println(" =======亚冠和走势/总和走势===== 耗时 ：{}" + (System.currentTimeMillis() - starttime));
        return resultMap;
    }
}
