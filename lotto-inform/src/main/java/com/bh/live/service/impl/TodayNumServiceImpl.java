package com.bh.live.service.impl;

import com.bh.live.common.enums.VarietyTypeEnum;
import com.bh.live.common.longDragon.LocationShadeCalc;
import com.bh.live.common.longDragon.TimeCalc;
import com.bh.live.common.utils.MapSortUtils;
import com.bh.live.dao.TodayNumDao;
import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.pojo.res.inform.TodayNumRes;
import com.bh.live.service.TodayNumService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TodayNumServiceImpl implements TodayNumService {

    @Resource
    private TodayNumDao todayNumDao;

    @Override
    public Map queryTodayNum(Integer varietyType) {
        Map retMap = Maps.newHashMap();

        //当天开奖数据
        List<StatHistoryDraw> list = todayNumDao.queryTodayNum(varietyType);

        //计算
        LocationShadeCalc calc = new LocationShadeCalc(varietyType);

        calc.init();

        Map<List<Integer>, Long> param = Maps.newHashMap();

        for (StatHistoryDraw draw : list) {
            List num = Arrays.asList(draw.getOpenCode().split(",")).stream().
                    map(item -> Integer.valueOf(item.trim())).collect(Collectors.toList());

            calc.execute(num);

            param.put(num, Long.valueOf(draw.getExpect()));
        }

        //总开期数
        Map<Integer, Map<Integer, Integer>> map_1 = calc.locationShadeMapping;

        Map<Integer, Map<Integer, Long>> map_2 = null;
        //未开期数
        if (list.size() > 0) {
            map_2 = getInterval(param, Long.valueOf(list.get(0).getExpect()),
                    list.size(), varietyType);
        }

        retMap.put("data", getData(map_1, map_2, varietyType));
        return retMap;
    }


    /**
     * 每个号码在每个位置未开的次数(离最新一期的开奖间隔期数)
     *
     * @param param
     * @param expect      当天最新一期期数
     * @param totalExpect 当天已开期数
     * @return
     */
    public Map<Integer, Map<Integer, Long>> getInterval(Map<List<Integer>, Long> param, Long expect, Integer totalExpect, Integer varietyType) {

        TimeCalc timeCalc = new TimeCalc(varietyType);
        timeCalc.init();
        // 当天所有期数数据
        timeCalc.execute(MapSortUtils.sortByValueAscending(param));

        Map<Integer, Map<Integer, Long>> retMap = timeCalc.locationShadeMapping;
        for (Map.Entry<Integer, Map<Integer, Long>> entry : retMap.entrySet()) {
            for (Map.Entry<Integer, Long> entry2 : entry.getValue().entrySet()) {
                Long resultExpect = entry2.getValue();
                if (resultExpect == null) {
                    entry2.setValue(Long.valueOf(totalExpect));
                } else {
                    entry2.setValue(expect - resultExpect);
                }
            }
        }
        return retMap;
    }

    /**
     * 返回今日号码列表
     * 0, "幸运飞艇"
     * 1, "北京赛车"
     * 2, "重庆时时彩"
     * 3, "圣地彩"
     * 4, "江苏快3"
     * 5, "幸运农场"
     * 6, "北京快乐8"
     * 7, "新疆时时彩"
     * 8, "广东快了十分"
     * 9, "广东11选5"
     * 10, "天津时时彩"
     * 11, "香港彩"
     *
     * @param map1
     * @param map2
     * @return
     */
    public List<TodayNumRes> getData(Map<Integer, Map<Integer, Integer>> map1,
                                     Map<Integer, Map<Integer, Long>> map2, Integer varietyType) {
        int startNum = 0;
        int endNum = 0;
        if (varietyType == VarietyTypeEnum.get(0).getCode() || varietyType == VarietyTypeEnum.get(1).getCode()) {
            startNum = 1;
            endNum = 10;
        } else if (varietyType == VarietyTypeEnum.get(2).getCode()
                || varietyType == VarietyTypeEnum.get(7).getCode()
                || varietyType == VarietyTypeEnum.get(10).getCode()) {
            startNum = 0;
            endNum = 9;
        } else if (varietyType == VarietyTypeEnum.get(5).getCode() || varietyType == VarietyTypeEnum.get(8).getCode()) {
            startNum = 1;
            endNum = 20;
        } else if (varietyType == VarietyTypeEnum.get(9).getCode()) {
            startNum = 1;
            endNum = 11;
        }
        List<TodayNumRes> retList = new ArrayList<>();
        for (int m = startNum; m <= endNum; m++) {
            TodayNumRes todayNumDto = new TodayNumRes();
            todayNumDto.setBallNum(m);
            todayNumDto.setOneOpenCount(map1.get(1).get(m).intValue());
            todayNumDto.setOneNotOpenCount(map2 == null ? 0 : map2.get(1).get(m).intValue());
            todayNumDto.setTwoOpenCount(map1.get(2).get(m).intValue());
            todayNumDto.setTwoNotOpenCount(map2 == null ? 0 :map2.get(2).get(m).intValue());
            todayNumDto.setThreeOpenCount(map1.get(3).get(m).intValue());
            todayNumDto.setThreeNotOpenCount(map2 == null ? 0 :map2.get(3).get(m).intValue());
            todayNumDto.setFourOpenCount(map1.get(4).get(m).intValue());
            todayNumDto.setFourNotOpenCount(map2 == null ? 0 :map2.get(4).get(m).intValue());
            todayNumDto.setFiveOpenCount(map1.get(5).get(m).intValue());
            todayNumDto.setFiveNotOpenCount(map2 == null ? 0 :map2.get(5).get(m).intValue());

            if( varietyType == VarietyTypeEnum.get(5).getCode() || varietyType == VarietyTypeEnum.get(8).getCode()){
                todayNumDto.setSixOpenCount(map1.get(6).get(m).intValue());
                todayNumDto.setSixNotOpenCount(map2 == null ? 0 :map2.get(6).get(m).intValue());
                todayNumDto.setSevenOpenCount(map1.get(7).get(m).intValue());
                todayNumDto.setSevenNotOpenCount(map2 == null ? 0 :map2.get(7).get(m).intValue());
                todayNumDto.setEightOpenCount(map1.get(8).get(m).intValue());
                todayNumDto.setEightNotOpenCount(map2 == null ? 0 :map2.get(8).get(m).intValue());
            }

            if (varietyType == VarietyTypeEnum.get(0).getCode() || varietyType == VarietyTypeEnum.get(1).getCode()
                     || varietyType == VarietyTypeEnum.get(9).getCode()) {
                todayNumDto.setSixOpenCount(map1.get(6).get(m).intValue());
                todayNumDto.setSixNotOpenCount(map2 == null ? 0 :map2.get(6).get(m).intValue());
                todayNumDto.setSevenOpenCount(map1.get(7).get(m).intValue());
                todayNumDto.setSevenNotOpenCount(map2 == null ? 0 :map2.get(7).get(m).intValue());
                todayNumDto.setEightOpenCount(map1.get(8).get(m).intValue());
                todayNumDto.setEightNotOpenCount(map2 == null ? 0 :map2.get(8).get(m).intValue());
                todayNumDto.setNineOpenCount(map1.get(9).get(m).intValue());
                todayNumDto.setNineNotOpenCount(map2 == null ? 0 :map2.get(9).get(m).intValue());
                todayNumDto.setTenOpenCount(map1.get(10).get(m).intValue());
                todayNumDto.setTenNotOpenCount(map2 == null ? 0 :map2.get(10).get(m).intValue());

            } else if (varietyType == VarietyTypeEnum.get(9).getCode()) {
                todayNumDto.setElevenOpenCount(map1.get(11).get(m).intValue());
                todayNumDto.setElevenNotOpenCount(map2 == null ? 0 :map2.get(11).get(m).intValue());

            }
            retList.add(todayNumDto);

        }
        return retList;
    }
}
