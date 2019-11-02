package com.bh.live.common.utils;

import com.bh.live.pojo.res.inform.SpecialFormStatisticsRes;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 特殊形态统计匹配规则
 *
 * @author Administrator
 * @title: FormSpecialUtil
 * @projectName live
 * @description: TODO
 * @date 2019/6/20  19:43
 */
public class FormSpecialUtil {

    /**
     * 得到匹配成功的数据
     */
    private static String result = null;
    /**
     * 匹配成功的坐标值
     */
    private static int findIndex = -1;

    /**
     * 生成形态
     *
     * @param lists
     */
    public static List<Map<Integer, String>> formStat(List<SpecialFormStatisticsRes> lists) {
        System.out.println("----------------------特殊形态开始统计----------------------------");
        // 首次结果
        System.out.println("匹配到的结果为：" + result);
        return beginForm(lists);

    }


    /**
     * 开始匹配数据
     *
     * @param lists
     */
    private static List<Map<Integer, String>> beginForm(List<SpecialFormStatisticsRes> lists) {
        result = null;
        StringBuilder bigSamll = new StringBuilder();
        StringBuilder singleDouble = new StringBuilder();
        StringBuilder dragonTiger = new StringBuilder();
        // 生成字符串
        for (SpecialFormStatisticsRes specialFormStatisticsVo : lists) {
            bigSamll.append(specialFormStatisticsVo.getBigSmall());
            singleDouble.append(specialFormStatisticsVo.getSingleDouble());
            dragonTiger.append(specialFormStatisticsVo.getDragonTiger());
        }

        System.out.println("大小：" + bigSamll);
        System.out.println("单双：" + singleDouble);
        System.out.println("龙虎: " + dragonTiger);
        System.out.println("大小长度：" + bigSamll.length());


        return match(bigSamll, singleDouble, dragonTiger);

    }

    /**
     * 循环匹配单双大小龙虎
     *
     * @param bigSamll
     * @param singleDouble
     * @param
     */
    private static List<Map<Integer, String>> match(StringBuilder bigSamll, StringBuilder singleDouble, StringBuilder dragonTiger) {

        String[] bigSmalls = {"(?:大小|小大){2,}", "(?:大小|小大){2,}[大小]", "(?:大小小|小大大){1,}","(?:大小小|小大大){1,}[大小]",
                "(?:大小小|小大大){1,}[大小]{2}", "(?:大大小小|小小大大){1,}", "(?:大大小小|小小大大){1,}[大小]",
                "(?:大大小小|小小大大){1,}[大小]{2}","(?:大大小小|小小大大){1,}[大小]{3}"};
        String[] singleDoubles = {"(?:单双|双单){2,}", "(?:单双|双单){2,}[单双]", "(?:单双双|双单单){1,}","(?:单双双|双单单){1,}[单双]",
                "(?:单双双|双单单){1,}[单双]{2}", "(?:单单双双|双双单单){1,}", "(?:单单双双|双双单单){1,}[单双]",
                "(?:单单双双|双双单单){1,}[单双]{2}","(?:单单双双|双双单单){1,}[单双]{3}"};
        String[] dragonTigers = {"(?:龙虎|虎龙){2,}", "(?:龙虎|虎龙){2,}[龙虎]", "(?:龙虎虎|虎龙龙){1,}",
                "(?:龙虎虎|虎龙龙){1,}[龙虎]{2}", "(?:龙龙虎虎|虎虎龙龙){1,}", "(?:龙龙虎虎|虎虎龙龙){1,}[龙虎]",
                "(?:龙龙虎虎|虎虎龙龙){1,}[龙虎]{2}","(?:龙龙虎虎|虎虎龙龙){1,}[龙虎]{3}"};
//        String[] bigSmalls = {"(?:大小|小大){2,}","(?:大小|小大){2,}[大小]","(?:单双|双单){2,}","(?:单双|双单){2,}[单双]", "(?:龙虎|虎龙){2,}","(?:龙虎|虎龙){2,}[龙虎]"};
//        String[] singleDoubles = {"(?:大小小|小大大){2,}","(?:大小小|小大大){2,}[大小]","(?:单双双|双单单){2,}", "(?:单双双|双单单){2,}[单双]","(?:龙虎虎|虎龙龙){2,}","(?:龙虎虎|虎龙龙){2,}[龙虎]"};
//        String[] dragonTigers = {"(?:大大小小|小小大大){1,}", "(?:大大小小|小小大大){1,}[大小]","(?:单单双双|双双单单){1,}", "(?:单单双双|双双单单){1,}[单双]", "(?:龙龙虎虎|虎虎龙龙){1,}", "(?:龙龙虎虎|虎虎龙龙){1,}[龙虎]"};
//
        List<Map<Integer, String>> list = new ArrayList();
        List<Map<String, Integer>> newList = new ArrayList();
        List<Map<String, Integer>> recursionList = null;
        for (int i = 0; i < bigSmalls.length; i++) {
            recursionList = recursion(bigSamll, bigSmalls[i], newList);
        }
        for (int i = 0; i < singleDoubles.length; i++) {
            recursionList = recursion(singleDouble, singleDoubles[i], newList);

        }
        for (int i = 0; i < dragonTigers.length; i++) {
            recursionList = recursion(dragonTiger, dragonTigers[i], newList);

        }
        System.out.println("--------------" + recursionList.toString());
        Map<Integer, String> map = new HashMap();
        for (int i = 0; i < recursionList.size(); i++) {
            Map<String, Integer> newMap = recursionList.get(i);
            Object[] objects = newMap.keySet().toArray();
            for (int j = 0; j < objects.length; j++) {
                Integer integer = newMap.get(objects[j]);
                Integer number = Integer.parseInt(objects[j].toString().substring(0, objects[j].toString().indexOf("-")));
                String type = objects[j].toString().substring(objects[j].toString().indexOf("-") + 1);
                if (type.equals("bg")) {//大小
                    if (map.containsKey(number)) {
                        map.put(number, map.get(number) + "," + integer);
                    } else {
                        map.put(number, integer.toString());
                    }
                }
                if (type.equals("bgg")) {//大小小
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 2) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }

                    } else {
                        map.put(number, 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 2) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }

                    }

                }
                if (type.equals("bbgg")) {//大大小小
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 3) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }


                    } else {
                        map.put(number, 0 + "," + 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 3) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }
                    }

                }
                if (type.equals("sd")) {//单双
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 4) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }
                    } else {
                        map.put(number, 0 + "," + 0 + "," + 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 4) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }
                    }

                }
                if (type.equals("sdd")) {//单双双
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 5) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }

                    } else {
                        map.put(number, 0 + "," + 0 + "," + 0 + "," + 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 5) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }
                    }

                }
                if (type.equals("ssdd")) {//单单双双
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 6) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }

                    } else {
                        map.put(number, 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 6) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }
                    }

                }
                if (type.equals("dt")) {//龙虎
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 7) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }

                    } else {
                        map.put(number, 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 7) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }
                    }

                }
                if (type.equals("dtt")) {//龙虎虎
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 8) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }

                    } else {

                        map.put(number, 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 8) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }
                    }

                }
                if (type.equals("ddtt")) {//龙龙虎虎
                    if (map.containsKey(number)) {
                        if (map.get(number).split(",").length == 9) {
                            String[] split = map.get(number).split(",");
                            String str = split[split.length - 1];
                            if (str.equals("0")) {
                                str = integer.toString();
                                split[split.length - 1] = str;
                                map.put(number, StringUtils.join(split, ","));
                            }
                        } else {
                            map.put(number, map.get(number) + "," + integer);

                        }

                    } else {
                        map.put(number, 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + integer);
                    }
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        String[] split = entry.getValue().split(",");
                        if (split.length < 9) {
                            map.put(entry.getKey(), entry.getValue() + "," + 0);
                        }
                    }


                }

            }
        }
        list.add(map);
        System.out.println(list.toString());
        return list;
    }

    /**
     * 递归调用判断
     *
     * @param context
     * @param pat
     * @return
     */
    public static List<Map<String, Integer>> recursion(StringBuilder context, String pat, List<Map<String, Integer>> newList) {
        //(?:大小|小大){5,}
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(context);
        String type = null;//类型
        Map<String, Integer> map = new HashMap<>();
        while (m.find()) {
            String dateMeta = m.group();
            if (pat.indexOf("大小|小大") > 0) {
                type = "bg";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("大小小|小大大") > 0) {
                type = "bgg";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("大大小小|小小大大") > 0) {
                type = "bbgg";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("单双|双单") > 0) {
                type = "sd";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("单双双|双单单") > 0) {
                type = "sdd";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("单单双双|双双单单") > 0) {
                type = "ssdd";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("龙虎|虎龙") > 0) {
                type = "dt";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("龙虎虎|虎龙龙") > 0) {
                type = "dtt";
                setMap(map, dateMeta, type);
            }
            if (pat.indexOf("龙龙虎虎|虎虎龙龙") > 0) {
                type = "ddtt";
                setMap(map, dateMeta, type);
            }


        }
        newList.add(map);
        return newList;
    }

    /**
     * 把数据封装成map
     *
     * @param
     * @param dateMeta
     * @param type
     */
    private static void setMap(Map<String, Integer> map, String dateMeta, String type) {
        if (dateMeta.length() > 4) {
            Integer length = dateMeta.length();
            if (!map.containsKey(length + "-" + type)) {
                map.put(length + "-" + type, 1);
            } else {
                map.put(length + "-" + type, map.get(length + "-" + type) + 1);
            }
        }
    }

}
