package com.bh.live.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.enums.VarietyTypeEnum;
import com.bh.live.common.longDragon.HongKongCalc;
import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.MapSortUtils;
import com.bh.live.dao.StatHistoryDrawDao;
import com.bh.live.dao.StatHistoryStatisticsDao;
import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.model.inform.ZodiacFivePhases;
import com.bh.live.service.HistoryStatisticsService;
import com.bh.live.service.ZodiacFivePhasesService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HistoryStatisticsServiceImpl implements HistoryStatisticsService {

    @Resource
    private StatHistoryStatisticsDao drawDao;

    @Resource
    private ZodiacFivePhasesService zodiacFivePhasesService;

    @Resource
    private StatHistoryDrawDao statHistoryDrawDao;

    @Resource
    private RedisManager redisManager;

    /**
     * 時時彩  ---- 综合统计
     *
     * @return
     */
    @Override
    public List<Map<String, BigDecimal>> pageOne(Integer varietyType) {
        List<Map<String, BigDecimal>> retListMaps = new LinkedList<>();
        List<Map<String, BigDecimal>> maps = new LinkedList<>();
        if (varietyType == 5 || varietyType == 8) {
            //幸运农场 ------广东快乐十分
            maps = drawDao.statHistoryNumZh20(varietyType);
            for (int i = 0; i < maps.size(); i++) {
                Map<String, BigDecimal> map = maps.get(i);
                maps.get(i).put("bigCount", map.get("elevenCount").add(map.get("twelveCount")).add(map.get("thirteenCount")).add(map.get("fourteenCount")).add(map.get("fiveteenCount"))
                        .add(map.get("sixteenCount")).add(map.get("seventeenCount")).add(map.get("eighteenCount")).add(map.get("nineteenCount")).add(map.get("twentyCount")));
                maps.get(i).put("smallCount", map.get("oneCount").add(map.get("twoCount")).add(map.get("threeCount")).add(map.get("fourCount")).add(map.get("fiveCount"))
                        .add(map.get("sixCount")).add(map.get("sevenCount")).add(map.get("eightCount")).add(map.get("nineCount")).add(map.get("tenCount")));
                maps.get(i).put("singleCount", map.get("oneCount").add(map.get("threeCount")).add(map.get("fiveCount")).add(map.get("sevenCount")).add(map.get("nineCount"))
                        .add(map.get("elevenCount")).add(map.get("thirteenCount")).add(map.get("fiveteenCount")).add(map.get("seventeenCount")).add(map.get("nineteenCount")));
                maps.get(i).put("doubleCount", map.get("twoCount").add(map.get("fourCount")).add(map.get("sixCount")).add(map.get("eightCount")).add(map.get("tenCount"))
                        .add(map.get("twelveCount")).add(map.get("fourteenCount")).add(map.get("sixteenCount")).add(map.get("eighteenCount")).add(map.get("twentyCount")));
            }
            return maps;
        } else if (varietyType == 4) {
            //江苏快3
            maps = drawDao.statHistoryZh(varietyType);
            for (int i = 0; i < maps.size(); i++) {
                Map<String, BigDecimal> retMap = Maps.newHashMap();
                Map<String, BigDecimal> map = maps.get(i);
                retMap.put("openTime", map.get("openTime"));
                retMap.put("oneCount", map.get("one1").add(map.get("one2").add(map.get("one3"))));
                retMap.put("twoCount", map.get("two1").add(map.get("two2").add(map.get("two3"))));
                retMap.put("threeCount", map.get("three1").add(map.get("three2").add(map.get("three3"))));
                retMap.put("fourCount", map.get("four1").add(map.get("four2").add(map.get("four3"))));
                retMap.put("fiveCount", map.get("five1").add(map.get("five2").add(map.get("five3"))));
                retMap.put("sixCount", map.get("six1").add(map.get("six2").add(map.get("six3"))));
                retMap.put("bigCount", retMap.get("fourCount").add(retMap.get("fiveCount")).add(retMap.get("sixCount")));
                retMap.put("smallCount", retMap.get("oneCount").add(retMap.get("twoCount")).add(retMap.get("threeCount")));
                retListMaps.add(retMap);
            }
            maps.clear();
            return retListMaps;
        } else {
            maps = drawDao.statHistoryZh(varietyType);
            for (int i = 0; i < maps.size(); i++) {
                Map<String, BigDecimal> retMap = Maps.newHashMap();
                Map<String, BigDecimal> map = maps.get(i);
                retMap.put("openTime", map.get("openTime"));
                //0号球在(a1,a2,a3,a4,a5) 一号位置出现的次数，二号位置出现的次数，三号位置出现的次数。。。。
                retMap.put("zeroCount", map.get("one1").add(map.get("one2").add(map.get("one3").add(map.get("one4").add(map.get("one5"))))));
                retMap.put("oneCount", map.get("two1").add(map.get("two2").add(map.get("two3").add(map.get("two4").add(map.get("two5"))))));
                retMap.put("twoCount", map.get("three1").add(map.get("three2").add(map.get("three3").add(map.get("three4")).add(map.get("three5")))));
                retMap.put("threeCount", map.get("four1").add(map.get("four2").add(map.get("four3").add(map.get("four4").add(map.get("four5"))))));
                retMap.put("fourCount", map.get("five1").add(map.get("five2").add(map.get("five3").add(map.get("five4").add(map.get("five5"))))));
                retMap.put("fiveCount", map.get("six1").add(map.get("six2").add(map.get("six3").add(map.get("six4").add(map.get("six5"))))));
                retMap.put("sixCount", map.get("seven1").add(map.get("seven2").add(map.get("seven3").add(map.get("seven4").add(map.get("seven5"))))));
                retMap.put("sevenCount", map.get("eight1").add(map.get("eight2").add(map.get("eight3").add(map.get("eight4").add(map.get("eight5"))))));
                retMap.put("eightCount", map.get("nine1").add(map.get("nine2").add(map.get("nine3").add(map.get("nine4").add(map.get("nine5"))))));
                retMap.put("nineCount", map.get("ten1").add(map.get("ten2").add(map.get("ten3").add(map.get("ten4").add(map.get("ten5"))))));
                retMap.put("bigCount", retMap.get("fiveCount").add(retMap.get("sixCount").add(retMap.get("sevenCount").add(retMap.get("eightCount").add(retMap.get("nineCount"))))));
                retMap.put("smallCount", retMap.get("zeroCount").add(retMap.get("oneCount").add(retMap.get("twoCount").add(retMap.get("threeCount").add(retMap.get("fourCount"))))));
                retMap.put("singleCount", retMap.get("oneCount").add(retMap.get("threeCount").add(retMap.get("fiveCount").add(retMap.get("sevenCount").add(retMap.get("nineCount"))))));
                retMap.put("doubleCount", retMap.get("zeroCount").add(retMap.get("twoCount").add(retMap.get("fourCount").add(retMap.get("sixCount").add(retMap.get("eightCount"))))));
                retMap.put("longCount", map.get("longCount"));
                retMap.put("huCount", map.get("huCount"));
                retMap.put("heCount", map.get("heCount"));
                retListMaps.add(retMap);
            }
            maps.clear();
            return retListMaps;
        }
    }

    /**
     * 時時彩-- 单双大小统计
     *
     * @return
     */
    @Override
    public List<Map<String, Integer>> pageTwo(Integer varietyType) {
        if (varietyType == 5 || varietyType == 8) {
            //幸运农场 ------广东快乐十分
            return drawDao.statHistory20SingleDouble(varietyType);
        } else {
            return drawDao.statHistorySingleDouble(varietyType);
        }
    }

    /**
     * 香港彩  特殊号  统计
     * type  1: 特码热门  2：特码尾数热门  3：正码热门  4：生肖特码热门  5：生肖正码热门  6：波色特码热门 7：波色正码热门
     *
     * @param
     * @return
     */
    @Override
    public Map<String, Object> statistics(Integer expect, Integer type) {
        Map<String, Object> retMap = Maps.newHashMap();
        List<Map<Object, Integer>> maps = new ArrayList<>();
        Map<String, Object> param = Maps.newHashMap();
        List<StatHistoryDraw> list = null;
        List<ZodiacFivePhases> sxList = null;
        String ball = null;
        JSONObject legend = new JSONObject();
        JSONArray legendData = new JSONArray();

        if (type == 4 || type == 5 || type == 6 || type == 7) {
            param.put("expect", expect);
            param.put("date", null);
            param.put("varietyType", VarietyTypeEnum.get(11).getCode());

            //五行生肖
            Map<String, Object> map = zodiacFivePhasesService.queryZodiacFivePhases((Integer.parseInt(DateUtils.formatDate(new Date()).split("-")[0])));
            // 香港彩歷史數據
            list = statHistoryDrawDao.queryHkHistoryDraw(param);
            if (type == 4 || type == 5) {
                // 生肖
                sxList = (List<ZodiacFivePhases>) map.get("zodiacList");
            } else {
                // 波色
                sxList = (List<ZodiacFivePhases>) map.get("waveColorList");
            }
        }

        int startNum = 0;
        int endNum = 0;

        if (type == 1) {
            legendData.add("特碼熱門");
            startNum = 1;
            endNum = 49;
            ball = "number_seven_ball";
            maps = drawDao.querySpecialCode(expect, ball, type);
        } else if (type == 2) {
            legendData.add("特碼尾數熱門");
            startNum = 0;
            endNum = 9;
            ball = "number_seven_ball";
            maps = drawDao.querySpecialCode(expect, ball, type);
        } else if (type == 3) {
            legendData.add("正码熱門");
            List<String> codelist = drawDao.queryHongKong(expect);
            HongKongCalc calc = new HongKongCalc(type);
            calc.init();
            codelist.stream().forEach(vo -> {
                calc.execute(Arrays.asList(vo.split(",")).stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList()));
            });
            maps = calc.hongKongMap;

            //排序
            maps = MapSortUtils.sortByValue(maps,true);


        } else if (type == 4 || type == 5) {
            legendData.add(type == 4 ? "生肖特碼熱門" : "生肖正碼熱門");
            return getMap(retMap, legend, legendData, type, list, sxList);
        } else if (type == 6 || type == 7) {
            legendData.add(type == 6 ? "波色特碼熱門" : "波色正码碼熱門");
            return getMap(retMap, legend, legendData, type, list, sxList);
        }

        // 标题
        legend.put("data", legendData);

        //x轴数据
        JSONObject xAxis = new JSONObject();
        //y轴数据
        JSONObject yAxis = new JSONObject();

        JSONArray xArr = new JSONArray();
        JSONArray yArr = new JSONArray();

        for (int i = 0; i < maps.size(); i++) {
            Map<Object, Integer> mp = maps.get(i);
            if (type == 3) {
                mp.forEach((k,v)->{
                    xArr.add(k);
                    yArr.add(v);
                });
            } else if (type == 1 || type == 2) {
                Object x = mp.get("ball");
                xArr.add(Integer.parseInt(x.toString()));
                yArr.add(mp.get("total"));
            } else {
            }
        }
        xAxis.put("data", xArr);
        yAxis.put("data", yArr);

        if (type != 3) {
            for (int n = startNum; n <= endNum; n++) {
                if (!xAxis.getJSONArray("data").contains(n)) {
                    xAxis.getJSONArray("data").add(n);
                    yAxis.getJSONArray("data").add(0);
                }
            }
        }
        retMap.put("legend", legend);
        retMap.put("xAxis", xAxis);
        retMap.put("yAxis", yAxis);

        return retMap;
    }

    /**
     * 香港彩  生肖特碼 红波 蓝波 绿波 熱門 统计
     * @param retMap   返回图形数据集合
     * @param legend   图形数据legend 数据
     * @param legendData 图形数据legend 数据
     * @param type  1: 特码热门  2：特码尾数热门  3：正码热门  4：生肖特码热门  5：生肖正码热门  6：波色特码热门 7：波色正码热门
     * @param list  历史数据
     * @param sxList 生肖五行 对应的匹配数据集合
     * @return
     */
    public Map<String, Object> getMap(Map<String, Object> retMap, JSONObject legend, JSONArray legendData, int type,
                                      List<StatHistoryDraw> list, List<ZodiacFivePhases> sxList) {
        int mouse = 0, cattle = 0, tiger = 0, rabbit = 0, dragon = 0, snake = 0, horse = 0, sheep = 0, monkey = 0, chicken = 0, dog = 0, pig = 0, redWave = 0, blueWave = 0, greenWave = 0;
        if (type == 4 || type == 6) {
            for (ZodiacFivePhases item : sxList) {
                String targetName = item.getTargetName();
                List sx = Arrays.asList(item.getNumJson().split(","));
                for (StatHistoryDraw vo : list) {
                    String code = vo.getNumberSevenBall();
                    if (type == 4) {
                        if (targetName.equals("鼠") && sx.contains(code)) {
                            mouse++;
                        } else if (targetName.equals("牛") && sx.contains(code)) {
                            cattle++;
                        } else if (targetName.equals("虎") && sx.contains(code)) {
                            tiger++;
                        } else if (targetName.equals("兔") && sx.contains(code)) {
                            rabbit++;
                        } else if (targetName.equals("龙") && sx.contains(code)) {
                            dragon++;
                        } else if (targetName.equals("蛇") && sx.contains(code)) {
                            snake++;
                        } else if (targetName.equals("马") && sx.contains(code)) {
                            horse++;
                        } else if (targetName.equals("羊") && sx.contains(code)) {
                            sheep++;
                        } else if (targetName.equals("猴") && sx.contains(code)) {
                            monkey++;
                        } else if (targetName.equals("鸡") && sx.contains(code)) {
                            chicken++;
                        } else if (targetName.equals("狗") && sx.contains(code)) {
                            dog++;
                        } else if (targetName.equals("猪") && sx.contains(code)) {
                            pig++;
                        }
                    } else {
                        if (targetName.equals("红波") && sx.contains(code)) {
                            redWave++;
                        } else if (targetName.equals("蓝波") && sx.contains(code)) {
                            blueWave++;
                        } else if (targetName.equals("绿波") && sx.contains(code)) {
                            greenWave++;
                        }
                    }
                }
            }
        } else {
            for (ZodiacFivePhases item : sxList) {
                String targetName = item.getTargetName();
                List<String> sx = Arrays.asList(item.getNumJson().split(","));
                for (StatHistoryDraw vo : list) {
                    List code = Arrays.asList(vo.getOpenCode().substring(0, vo.getOpenCode().lastIndexOf(",")).split(","));
                    List<String> intersection = sx.stream().filter(x -> code.contains(x)).collect(Collectors.toList());
                    int count = intersection.size();
                    if (type == 5) {
                        if (targetName.equals("鼠")) {
                            mouse += count;
                        } else if (targetName.equals("牛")) {
                            cattle += count;
                        } else if (targetName.equals("虎")) {
                            tiger += count;
                        } else if (targetName.equals("兔")) {
                            rabbit += count;
                        } else if (targetName.equals("龙")) {
                            dragon += count;
                        } else if (targetName.equals("蛇")) {
                            snake += count;
                        } else if (targetName.equals("马")) {
                            horse += count;
                        } else if (targetName.equals("羊")) {
                            sheep += count;
                        } else if (targetName.equals("猴")) {
                            monkey += count;
                        } else if (targetName.equals("鸡")) {
                            chicken += count;
                        } else if (targetName.equals("狗")) {
                            dog += count;
                        } else if (targetName.equals("猪")) {
                            pig += count;
                        }
                    } else {
                        if (targetName.equals("红波")) {
                            redWave += count;
                        } else if (targetName.equals("蓝波")) {
                            blueWave += count;
                        } else if (targetName.equals("绿波")) {
                            greenWave += count;
                        }
                    }
                }
            }
        }

        // 标题
        legend.put("data", legendData);

        //x轴数据
        JSONObject xAxis = new JSONObject();
        //y轴数据
        JSONObject yAxis = new JSONObject();
        JSONArray xArr = new JSONArray();
        JSONArray yArr = new JSONArray();

        if (type == 4 || type == 5) {
            xArr.add("鼠");
            xArr.add("牛");
            xArr.add("虎");
            xArr.add("兔");
            xArr.add("龍");
            xArr.add("蛇");
            xArr.add("马");
            xArr.add("羊");
            xArr.add("猴");
            xArr.add("鸡");
            xArr.add("狗");
            xArr.add("猪");

            yArr.add(mouse);
            yArr.add(cattle);
            yArr.add(tiger);
            yArr.add(rabbit);
            yArr.add(dragon);
            yArr.add(snake);
            yArr.add(horse);
            yArr.add(sheep);
            yArr.add(monkey);
            yArr.add(chicken);
            yArr.add(dog);
            yArr.add(pig);
        } else {
            xArr.add("红波");
            xArr.add("蓝波");
            xArr.add("绿波");

            yArr.add(redWave);
            yArr.add(blueWave);
            yArr.add(greenWave);
        }

        xAxis.put("data", xArr);
        yAxis.put("data", yArr);
        retMap.put("legend", legend);
        retMap.put("xAxis", xAxis);
        retMap.put("yAxis", yAxis);

        return retMap;
    }
}
