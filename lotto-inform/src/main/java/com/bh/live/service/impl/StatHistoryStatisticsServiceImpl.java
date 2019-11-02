package com.bh.live.service.impl;

import com.bh.live.dao.StatHistoryStatisticsDao;
import com.bh.live.pojo.res.inform.StatHistoryStatisticsRes;
import com.bh.live.pojo.res.inform.StatStatisticsCrownCountRes;
import com.bh.live.pojo.res.inform.StatStatisticsDragonTigerRes;
import com.bh.live.service.StatHistoryStatisticsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title: StatHistoryStatisticsServiceImpl
 * @projectName live
 * @description: TODO
 * @date 2019/6/12  18:03
 */
@Service
public class StatHistoryStatisticsServiceImpl implements StatHistoryStatisticsService {

    @Resource
    private StatHistoryStatisticsDao statHistoryStatisticsDao;

    /**
     * 查询北京赛车历史统计(大小单双)
     *
     * @param param
     * @return
     */
    @Override
    public Object getHistoryStatisticsList(Map<String, Object> param) {
        List<StatHistoryStatisticsRes> historyStatisticsList = statHistoryStatisticsDao.getHistoryStatisticsList(param);
        Map<String, Object> map = new HashMap<>();
        map.put("historyStatisticsList", historyStatisticsList);
        return map;
    }

    /**
     * 查询北京赛车历史统计(冠亚和)
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getCrownSubCount(@Param("param") Map<String, Object> param) {
        List<StatStatisticsCrownCountRes> crownSubCount = statHistoryStatisticsDao.getCrownSubCount(param);
        Map<String, Object> map = new HashMap<>();
        map.put("crownSubCount", crownSubCount);
        return map;
    }

    /**
     * 查询北京赛车历史统计(龙虎)
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getDragonTigerList(@Param("param") Map<String, Object> param) {
        List<StatStatisticsDragonTigerRes> dragonTigerList = statHistoryStatisticsDao.getDragonTigerList(param);
        Map<String, Object> map = new HashMap<>();
        map.put("dragonTigerList", dragonTigerList);
        return map;
    }

    @Override
    public List<Map<String, Integer>> queryHongKongDragonTiger() {
        return statHistoryStatisticsDao.queryHongKongDragonTiger();
    }
}
