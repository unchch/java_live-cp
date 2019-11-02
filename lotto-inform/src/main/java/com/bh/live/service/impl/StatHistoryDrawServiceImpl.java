package com.bh.live.service.impl;

import com.bh.live.common.enums.VarietyTypeEnum;
import com.bh.live.common.longDragon.HistoryOpenUtils;
import com.bh.live.common.longDragon.LongDragonShadeCalc;
import com.bh.live.common.longDragon.LuckyFarmShadeCalc;
import com.bh.live.common.longDragon.SscShadeCalc;
import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.common.utils.CalcUtils;
import com.bh.live.common.utils.ModelUtil;
import com.bh.live.dao.StatHistoryDrawDao;
import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.pojo.res.inform.OpenStatisticsCodeRes;
import com.bh.live.pojo.res.inform.StatHistoryLongDragonRes;
import com.bh.live.service.StatHistoryDrawService;
import com.bh.live.service.ZodiacFivePhasesService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.bh.live.common.utils.CalcUtils.findDupicateInArray;


@Service
@Slf4j
public class StatHistoryDrawServiceImpl implements StatHistoryDrawService {

    @Resource
    private StatHistoryDrawDao drawDao;

    @Autowired
    private RedisManager redisManager;

    @Resource
    private ZodiacFivePhasesService zodiacFivePhasesService;

    /**
     * 历史开奖统计(包括：長龍連開提醒)
     * pk10
     *
     * @param date
     * @return
     */
    @Override
    public Map<String, Object> queryStatHistoryDraw(String date, int varietyType) {
        Map<String, Object> retMap = Maps.newHashMap();

        //历史数据
        List<StatHistoryDraw> draws = drawDao.queryStatHistoryDraw(date, VarietyTypeEnum.get(varietyType).getCode());

        if (varietyType == VarietyTypeEnum.get(1).getCode() || varietyType == VarietyTypeEnum.get(5).getCode()) {
            //長龍連開提醒
            List<StatHistoryLongDragonRes> longDragon = longDragonOpened(drawDao.queryStatHistoryDrawASC(varietyType), varietyType);
            retMap.put("longDragon", longDragon);
        }
        retMap.put("list", draws);
        return retMap;
    }

    /**
     * 今日雙面統計
     * 號碼>=6 为大 号码 <6 为小
     * 冠亞軍和
     * 冠亞和大小：“冠亞和值”大於11時，為“大”；小於或等於11時，為“小”
     * 冠亞和單雙：“冠亞和值”為單數時，顯示“單”；為雙數時，顯示“雙”
     * <p>
     * • 冠軍 龙/虎：“第壹名”車號大於“第十名”車號視為【龙】中獎、反之小於視為【虎】中獎。
     * • 亞軍 龙/虎：“第二名”車號大於“第九名”車號視為【龙】中獎、反之小於視為【虎】中獎。
     * • 第三名 龙/虎：“第三名”車號大於“第八名”車號視為【龙】中獎、反之小於視為【虎】中獎。
     * • 第四名 龙/虎：“第四名”車號大於“第七名”車號視為【龙】中獎、反之小於視為【虎】中獎。
     * • 第五名 龙/虎：“第五名”車號大於“第六名”車號視為【龙】中獎、反之小於視為【虎】中獎。
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Integer>> queryToday(Map<String, Object> param) {
        return drawDao.queryTodayStatistics(param);
    }

    /**
     * 長龍連開提醒
     * 最新一期开始 连续出现单双、大小、龍虎至少2次
     *
     * @param todayList   当天数据
     * @param varietyType 彩种类型
     * @return
     */
    public List<StatHistoryLongDragonRes> longDragonOpened(List<StatHistoryDraw> todayList, int varietyType) {
        List<StatHistoryLongDragonRes> statHistoryLongDragonDtos = new ArrayList<>();
        Map<Integer, Map<String, Integer>> retMap = null;

        if (varietyType == VarietyTypeEnum.get(1).getCode()) {
            // pk10
            //初始化计算方法
            LongDragonShadeCalc lsc = new LongDragonShadeCalc();
            lsc.init();
            for (StatHistoryDraw draw : todayList) {
                List num = Arrays.asList(draw.getOpenCode().split(",")).stream().
                        map(item -> Integer.valueOf(item.trim())).collect(Collectors.toList());
                lsc.execute(num);
            }
            retMap = lsc.location2ShadeMapping;
        } else if (varietyType == VarietyTypeEnum.get(2).getCode() || varietyType == VarietyTypeEnum.get(7).getCode()
                || varietyType == VarietyTypeEnum.get(10).getCode()) {
            //(时时彩)初始化计算方法
            SscShadeCalc calc = new SscShadeCalc();
            calc.init();
            for (StatHistoryDraw draw : todayList) {
                List num = Arrays.asList(draw.getOpenCode().split(",")).stream().
                        map(item -> Integer.valueOf(item.trim())).collect(Collectors.toList());
                calc.execute(num);
            }
            retMap = calc.sscLongDragonShadeMapping;
        } else {
            // 幸运飞艇
            LuckyFarmShadeCalc luck = new LuckyFarmShadeCalc();
            luck.init();
            for (StatHistoryDraw draw : todayList) {
                List num = Arrays.asList(draw.getOpenCode().split(",")).stream().
                        map(item -> Integer.valueOf(item.trim())).collect(Collectors.toList());
                luck.execute(num);
            }
            retMap = luck.LuckyFarmShadeMapping;
        }

        for (Map.Entry<Integer, Map<String, Integer>> entry : retMap.entrySet()) {
            Integer position = entry.getKey();
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                StatHistoryLongDragonRes dto = new StatHistoryLongDragonRes();
                String key = entry2.getKey();
                Integer value = entry2.getValue();
                if (value > 1) {
                    if (varietyType == 1) {
                        if (key.contains("亚冠和")) {
                            dto.setPosition("亚冠和");
                            dto.setData(String.format("%s%s%s", key.substring(3), value, "期"));
                        } else {
                            dto.setPosition(ModelUtil.getPosition(position));
                            dto.setData(String.format("%s%s%s", key, value, "期"));
                        }
                    } else if (varietyType == VarietyTypeEnum.get(2).getCode() || varietyType == VarietyTypeEnum.get(7).getCode()
                            || varietyType == VarietyTypeEnum.get(10).getCode()) {
                        if (key.contains("总和")) {
                            dto.setPosition("总和");
                            dto.setData(String.format("%s%s%s", key.substring(2), value, "期"));
                        } else if (key.contains("龙") || key.contains("虎")) {
                            dto.setPosition("龙虎");
                            dto.setData(String.format("%s%s%s", key, value, "期"));
                        } else {
                            dto.setPosition(ModelUtil.getSscPosition(position));
                            dto.setData(String.format("%s%s%s", key, value, "期"));
                        }
                    } else {
                        // 幸运飞艇
                        if (key.contains("总和")) {
                            dto.setPosition("总和");
                            dto.setData(String.format("%s%s%s", key.substring(2), value, "期"));
                        } else if (key.contains("龙") || key.contains("虎")) {
                            dto.setPosition(ModelUtil.getSscPosition(position));
                            dto.setData(String.format("%s%s%s", key, value, "期"));
                        } else {
                            dto.setPosition(ModelUtil.getSscPosition(position));
                            dto.setData(String.format("%s%s%s", key, value, "期"));
                        }
                    }
                    dto.setValue(value);
                    statHistoryLongDragonDtos.add(dto);
                }

            }
        }
        //倒序返回
        return statHistoryLongDragonDtos.stream().sorted((v1, v2) -> v2.getValue().compareTo(v1.getValue())).collect(Collectors.toList());
    }


    /**
     * 時時彩 今日号码统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> querySscStatistics(Integer varietyType) {
        Map retMap = Maps.newHashMap();
        //今天开奖数据
        List<StatHistoryDraw> list = drawDao.queryStatHistoryDrawByCondition(varietyType);
        HistoryOpenUtils historyOpenUtils = new HistoryOpenUtils();
        historyOpenUtils.init();
        int count = 0;
        for (StatHistoryDraw draw : list) {
            historyOpenUtils.execute(Arrays.asList(draw.getOpenCode().split(",")).stream().
                    map(item -> Integer.valueOf(item.trim())).collect(Collectors.toList()));

            //今日開出雙號期數
            if (CalcUtils.cheakIsRepeat(draw.getOpenCode().split(","))) {
                count++;
            }
        }
        //龍虎和
        List<Map<String, Integer>> maps = drawDao.querySscStatistics(varietyType);
        int zeroCount = 0, oneCount = 0, twoCount = 0, threeCount = 0, fourCount = 0, fiveCount = 0, sixCount = 0, sevenCount = 0, eightCount = 0, nineCount = 0, singleCount = 0, doubleCount = 0, bigCount = 0, smallCount = 0;

        for (Map.Entry<Integer, Map<Integer, Integer>> entry : historyOpenUtils.historyMapping.entrySet()) {
            for (Map.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
                int value = entry2.getValue();
                switch (entry.getKey()) {
                    case 0:
                        zeroCount += value;
                        break;
                    case 1:
                        oneCount += value;
                        break;
                    case 2:
                        twoCount += value;
                        break;
                    case 3:
                        threeCount += value;
                        break;
                    case 4:
                        fourCount += value;
                        break;
                    case 5:
                        fiveCount += value;
                        break;
                    case 6:
                        sixCount += value;
                        break;
                    case 7:
                        sevenCount += value;
                        break;
                    case 8:
                        eightCount += value;
                        break;
                    case 9:
                        nineCount += value;
                        break;
                }
            }
        }
        //单
        singleCount = oneCount + threeCount + fiveCount + sevenCount + nineCount;
        //双
        doubleCount = zeroCount + twoCount + fourCount + sixCount + eightCount;
        bigCount = fiveCount + sixCount + sevenCount + eightCount + nineCount;
        smallCount = zeroCount + oneCount + twoCount + threeCount + fourCount;

        retMap.put("zeroCount", zeroCount);
        retMap.put("oneCount", oneCount);
        retMap.put("twoCount", twoCount);
        retMap.put("threeCount", threeCount);
        retMap.put("fourCount", fourCount);
        retMap.put("fiveCount", fiveCount);
        retMap.put("sixCount", sixCount);
        retMap.put("sevenCount", sevenCount);
        retMap.put("eightCount", eightCount);
        retMap.put("nineCount", nineCount);
        retMap.put("singleCount", singleCount);
        retMap.put("doubleCount", doubleCount);
        retMap.put("bigCount", bigCount);
        retMap.put("smallCount", smallCount);
        retMap.put("longCount", maps.get(0).get("longCount"));
        retMap.put("huCount", maps.get(0).get("huCount"));
        retMap.put("heCount", maps.get(0).get("heCount"));
        //本期雙號
        retMap.put("doubleNum", list.size() > 0 ? findDupicateInArray(list.get(0).getOpenCode().split(",")) : 0);
        //今日開出雙號期數
        retMap.put("isRepeat", count);
        //當前重複開出遺漏
        getMap(retMap, varietyType);
        //今日最大遺漏
        getCurrentMaxOmit(retMap, varietyType);
        return retMap;
    }

    /**
     * 計算(下期重複開出期數)
     *
     * @param retMap
     * @param varietyType
     * @return
     */
    public Map<String, Integer> getMap(Map<String, Integer> retMap, Integer varietyType) {
        int nextRepeatCount = 0;
        List<OpenStatisticsCodeRes> list = drawDao.queryExpect(varietyType);
        for (OpenStatisticsCodeRes dto : list) {
            // 当前期 开出号码
            String currentOpenCode = dto.getBopencode();
            // 下期开出号码
            String nextOpenCode = dto.getAopencode();
            //判断是否出现双号
            String repeatCode = findDupicateInArray(currentOpenCode.split(","));
            if (StringUtils.isNoneBlank(repeatCode)) {
                List<String> strs = Arrays.asList(repeatCode.split(","));
                for (int i = 0; i < strs.size(); i++) {
                    if (nextOpenCode.contains(strs.get(i))) {
                        nextRepeatCount++;
                        break;
                    }
                }
            } else {
                continue;
            }
        }
        retMap.put("nextRepeatCount", nextRepeatCount);
        return retMap;
    }

    /**
     * 計算(當前重複開出遺漏,今日最大遺漏)
     *
     * @param retMap
     * @param varietyType
     * @return
     */
    public Map<String, Integer> getCurrentMaxOmit(Map<String, Integer> retMap, Integer varietyType) {
        boolean flag;
        int currentRepeatOmit = 0;
        Map<String, Integer> map = Maps.newHashMap();
        map.put("currentRepeatOmit", currentRepeatOmit);
        List<OpenStatisticsCodeRes> list = drawDao.queryExpect(varietyType);
        for (OpenStatisticsCodeRes dto : list) {
            flag = false;
            // 当前期 开出号码
            String currentOpenCode = dto.getBopencode();
            // 下期开出号码
            String nextOpenCode = dto.getAopencode();
            //判断是否出现双号
            String repeatCode = findDupicateInArray(currentOpenCode.split(","));

            if (StringUtils.isNoneBlank(repeatCode)) {
                List<String> strs = Arrays.asList(repeatCode.split(","));
                for (int i = 0; i < strs.size(); i++) {
                    if (nextOpenCode.contains(strs.get(i))) {
                        if (map.containsKey("currentRepeatOmit") && map.get("currentRepeatOmit") < currentRepeatOmit) {
                            map.put("currentRepeatOmit", currentRepeatOmit);
                        }
                        currentRepeatOmit = 0;
                        flag = true;
                        break;
                    }
                }
                if (!flag && (StringUtils.isNoneBlank(repeatCode)
                        || StringUtils.isNoneBlank(findDupicateInArray(nextOpenCode.split(","))))) {
                    currentRepeatOmit++;
                }
            } else {
                continue;
            }
        }

        retMap.put("currentRepeatOmit", currentRepeatOmit);
        retMap.put("maxOmit", map.get("currentRepeatOmit"));
        return retMap;
    }

    /**
     * 時時彩(列表)
     * 總和	龍虎 前三 中三	後三
     * 双	小	虎	对子 杂六 半顺 全顺 杂六
     * <p>
     * <p>
     * <p>
     * 快樂8
     * <p>
     * 大、小：如果開出的20個開獎數位和值大於810則為大，如果和值小於810則為小，如果值為810則為“和”，以此判斷輸贏。
     * 單、雙：如果開出的20個開獎數位和值能被2整除則為雙，否則為單，以此判斷輸贏
     * 号码1至40为上盘号码，41至80为下盘号码。开出的20个开奖号码中：如上盘号码占多数(超过10个)时，此局为上盘;下盘号码占多数时为下盘;上下盘号码在此局开出的数目相同时(各10个数字)，此局为和盘。
     * 五行，即总数五行
     * 开出的20个号码的总和分在5个段，以金、木、水、火、土命名：金(210～695)、木(696～763)、水(764～855)、火(856～923)和土(924～1410)。
     * 例如：开奖号码为01、04、05、10、11、13、20、27、30、32、33、36、40、47、54、59、61、64、67、79，总和是693，则总分数在210-695段内，则开出的是“金”，下注“金”为赢，反之则输
     * 號碼1至40為上盤號碼，41至80為下盤號碼。開出的20個開獎號碼中：如上盤號碼占多數（超過10個）時，此局為上盤；下盤號碼占多數時為下盤；上下盤號碼在此局開出的數目相同時（各10個數位），此局為和盤。
     *
     * @return
     */
    @Override
    public Map<String, Object> page(Map<String, Object> param) {
        Map<String, Object> retList = new HashMap<>();
        String date = param.containsKey("date") ? param.get("date").toString() : "";
        int varietyType = Integer.parseInt(param.get("varietyType").toString());
        List<StatHistoryDraw> draws = null;

        //香港彩
        if (varietyType == VarietyTypeEnum.get(11).getCode()) {
            param.put("date", date);
            draws = drawDao.queryHkHistoryDraw(param);
            //封装数据
            getHongKongData(draws, param);
        } else {
            //历史数据
            draws = drawDao.queryStatHistoryDraw(date, VarietyTypeEnum.get(varietyType).getCode());
            if (varietyType == VarietyTypeEnum.get(6).getCode()) {
                // 快8
                for (int i = 0; i < draws.size(); i++) {
                    List<Integer> list = Arrays.asList(draws.get(i).getOpenCode().split(",")).stream()
                            .map(item -> Integer.valueOf(item.trim())).collect(Collectors.toList());
                    Long left = list.stream().filter(vo -> vo >= 1 && vo <= 40).count();
                    Long right = list.stream().filter(vo -> vo > 40 && vo <= 80).count();
                    draws.get(i).setUpAndDown(left.compareTo(right) >= 0 ? (left.compareTo(right) > 0 ? "上" : "中") : "下");
                    String fiveElements = null;
                    if (draws.get(i).getCrownSub() >= 210 && draws.get(i).getCrownSub() <= 695) {
                        fiveElements = "金";
                    } else if (draws.get(i).getCrownSub() >= 696 && draws.get(i).getCrownSub() <= 763) {
                        fiveElements = "木";
                    } else if (draws.get(i).getCrownSub() >= 764 && draws.get(i).getCrownSub() <= 855) {
                        fiveElements = "水";
                    } else if (draws.get(i).getCrownSub() >= 856 && draws.get(i).getCrownSub() <= 923) {
                        fiveElements = "火";
                    } else if (draws.get(i).getCrownSub() >= 924 && draws.get(i).getCrownSub() <= 1410) {
                        fiveElements = "土";
                    }
                    draws.get(i).setFiveElements(fiveElements);
                }
            } else if (varietyType == VarietyTypeEnum.get(2).getCode()
                    || varietyType == VarietyTypeEnum.get(3).getCode()
                    || varietyType == VarietyTypeEnum.get(7).getCode()
                    || varietyType == VarietyTypeEnum.get(9).getCode()
                    || varietyType == VarietyTypeEnum.get(10).getCode()) {
                //时时彩
                for (int i = 0; i < draws.size(); i++) {
                    String[] openCode = draws.get(i).getOpenCode().split(",");
                    draws.get(i).setTopThree(CalcUtils.cheakNumber(Arrays.stream(Arrays.copyOfRange(openCode, 0, 3))
                            .mapToInt(Integer::parseInt).toArray()));
                    draws.get(i).setMiddleThree(CalcUtils.cheakNumber(Arrays.stream(Arrays.copyOfRange(openCode, 1, 4))
                            .mapToInt(Integer::parseInt).toArray()));
                    draws.get(i).setAfterThree(CalcUtils.cheakNumber(Arrays.stream(Arrays.copyOfRange(openCode, 2, 5))
                            .mapToInt(Integer::parseInt).toArray()));
                }
            }
        }
        //列表
        retList.put("pageList", draws);
        return retList;
    }

    /**
     * 香港彩数据封装
     * type  1: 特码热门  2：特码尾数热门  3：正码热门
     * 4：生肖特码热门  5：生肖正码热门  6：波色特码热门 7：波色正码热门
     *
     * @param draws
     * @param param
     * @return
     */
    public List<StatHistoryDraw> getHongKongData(List<StatHistoryDraw> draws, Map<String, Object> param) {
        //香港彩
        draws.stream().forEach(vo -> {
            vo.setExpect(vo.getExpect().substring(vo.getExpect().length() - 3));
            vo.setSpecialCodeBigSmall(Integer.parseInt(vo.getNumberSevenBall()) > 24 ? "大" : "小");
            vo.setSpecialCodeSingleDouble(Integer.parseInt(vo.getNumberSevenBall()) % 2 == 0 ? "双" : "单");
        });

//        int year =Integer.parseInt(param.get("date").toString());
//        int type = Integer.parseInt(param.get("type").toString());
//
//        HongKongMaxOmission hongKongMaxOmission = new HongKongMaxOmission(type,year);
//        hongKongMaxOmission.init();
//        hongKongMaxOmission.execute(draws);

        return draws;
    }

    /**
     * 时时彩
     * 长龍连开
     *
     * @param varietyType
     * @return
     */
    @Override
    public Map<String, Object> longDragon(Integer varietyType) {
        Map<String, Object> retList = new HashMap<>();
        //長龍連開提醒
        retList.put("longDragon", longDragonOpened(drawDao.queryStatHistoryDrawASC(varietyType), varietyType));
        return retList;
    }
}
