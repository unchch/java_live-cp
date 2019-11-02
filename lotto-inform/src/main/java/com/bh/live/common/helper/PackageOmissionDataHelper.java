package com.bh.live.common.helper;

import com.bh.live.common.TrendUtils.CalcOmit;
import com.bh.live.model.inform.Omission;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2019/7/1 20:39
 * @desc create by zhoumaofan on 2019-07-01
 * @Version 1.0
 */
public class PackageOmissionDataHelper {

    public static List<Omission> getPackageOmissionCount(int varietyType,
                                                         List<Omission> mission, String dateType,
                                                         Map<Integer, List<Omission>> map, List crowns) {
        Map<Object, List<Omission>> resMap = new HashMap<>();
        Map<Integer, List<Omission>> collect = mission.stream().collect(Collectors.groupingBy(Omission::getNumberBall));

        for (Object integer : crowns) {
            List<Omission> list = new ArrayList<>();
            list.add(mission.get(0)); //第一条数据必须放，
            for (Map.Entry<Integer, List<Omission>> entry : collect.entrySet()) {
                if (entry.getKey() == integer) {
                    list.addAll(entry.getValue());
                }
            }
            /*//放入当前冠亚和到list
            for (Omission omission : mission) {
                if (omission.getCrownSub().compareTo(Integer.valueOf(integer.toString())) == 0) {
                    list.add(omission);
                }
            }*/
            //查询上一个日期最新的一条数据
            if (dateType.equals("week") || dateType.equals("month")) {
                list.addAll(map.get(Integer.valueOf(integer.toString())));
            } else {
                list.add(mission.get(mission.size() - 1)); //今日的最后一条数据必须放
            }
            resMap.put(integer, list);
        }
        mission.clear();
        for (Map.Entry<Object, List<Omission>> entry : resMap.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            Long omissionCount = packageOmissionCount(entry.getValue(), varietyType);
            Omission omission = new Omission();
            omission.setCrownSub(Integer.valueOf(entry.getKey().toString()));
            if (dateType.equals("today")) {
                omission.setTodayOmi(omissionCount);
            }
            if (dateType.equals("week")) {
                omission.setThisWeekOmi(omissionCount);
            }
            if (dateType.equals("month")) {
                omission.setThisMonthOmi(omissionCount);
            }
            mission.add(omission);
        }
        return mission;
    }

    /**
     * 获取冠亚和今日遗漏，封装遗漏数据（数字类的算法）
     * 计算最大遗漏
     *
     * @param todayOmission
     * @return
     */
    public static Long packageOmissionCount(List<Omission> todayOmission, int varietyType) {
        Long count = 0L;
        //如果只有两条数据，证明今日没有开出该冠亚和
        if (todayOmission.size() == 2) {
            //如果两条数据一样，证明只开了一期
            if (todayOmission.get(0).getExpect().compareTo(todayOmission.get(1).getExpect()) == 0) {
                count = 0L;
            } else {
                if (varietyType != 1) {
                    count = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(0).getExpect().toString(), todayOmission.get(1).getExpect().toString()) + 1);
                } else {
                    count = todayOmission.get(0).getExpect() - todayOmission.get(1).getExpect() + 1;
                }
            }
            return count;
        } else {
            List<Long> subList = new ArrayList<>();
            int size = todayOmission.size();
            for (int i = 0; i < size; i++) {
                if ((i + 1) != size) {
                    Long sub = 0L;
                    if (size > 3) {
                        //与头尾相等的情况
                        if ((todayOmission.get(0).getExpect().compareTo(todayOmission.get(1).getExpect()) == 0)
                                && (todayOmission.get(size - 2).getExpect().compareTo(todayOmission.get(size - 1).getExpect()) == 0)) {
                            if (varietyType != 1) {
                                sub = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(i).getExpect().toString(), todayOmission.get(i + 1).getExpect().toString()) - 1);
                            } else {
                                sub = todayOmission.get(i).getExpect() - todayOmission.get(i + 1).getExpect() - 1;
                            }
                        } else {
                            if (todayOmission.get(i).getCrownSub().compareTo(todayOmission.get(i + 1).getCrownSub()) == 0) {
                                if (varietyType != 1) {
                                    sub = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(i).getExpect().toString(), todayOmission.get(i + 1).getExpect().toString()) - 1);
                                } else {
                                    sub = todayOmission.get(i).getExpect() - todayOmission.get(i + 1).getExpect() - 1;
                                }
                            } else {
                                if (varietyType != 1) {
                                    sub = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(i).getExpect().toString(), todayOmission.get(i + 1).getExpect().toString()));
                                } else {
                                    sub = todayOmission.get(i).getExpect() - todayOmission.get(i + 1).getExpect();
                                }
                            }
                        }
                    } else {
                        //如果三条记录都一样，代表只开出了一期
                        if ((todayOmission.get(0).getExpect().compareTo(todayOmission.get(1).getExpect()) == 0)
                                && (todayOmission.get(1).getExpect().compareTo(todayOmission.get(2).getExpect()) == 0)) {
                            count = 0L;
                        } else if (!(todayOmission.get(0).getCrownSub().compareTo(todayOmission.get(1).getCrownSub()) == 0)
                                && !(todayOmission.get(1).getCrownSub().compareTo(todayOmission.get(2).getCrownSub()) == 0)) {
                            //19,18,19(不减1)
                            if (varietyType != 1) {
                                Long sub1 = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(0).getExpect().toString(), todayOmission.get(1).getExpect().toString()));
                                Long sub2 = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(1).getExpect().toString(), todayOmission.get(2).getExpect().toString()));
                                sub = sub1 > sub2 ? sub1 : sub2;
                            } else {
                                Long sub1 = todayOmission.get(0).getExpect() - todayOmission.get(1).getExpect();
                                Long sub2 = todayOmission.get(1).getExpect() - todayOmission.get(2).getExpect();
                                sub = sub1 > sub2 ? sub1 : sub2;
                            }
                        } else if ((todayOmission.get(0).getCrownSub().compareTo(todayOmission.get(1).getCrownSub()) == 0)
                                && !(todayOmission.get(1).getCrownSub().compareTo(todayOmission.get(2).getCrownSub()) == 0)) {
                            //19,19,18(不减1)
                            if (varietyType != 1) {
                                Long sub1 = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(0).getExpect().toString(), todayOmission.get(1).getExpect().toString()));
                                Long sub2 = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(1).getExpect().toString(), todayOmission.get(2).getExpect().toString()));
                                sub = sub1 > sub2 ? sub1 : sub2;
                            } else {
                                Long sub1 = todayOmission.get(0).getExpect() - todayOmission.get(1).getExpect();
                                Long sub2 = todayOmission.get(1).getExpect() - todayOmission.get(2).getExpect();
                                sub = sub1 > sub2 ? sub1 : sub2;
                            }
                        } else {
                            //三条记录不一样的情况18,19,19(减1)
                            if (varietyType != 1) {
                                Long sub1 = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(0).getExpect().toString(), todayOmission.get(1).getExpect().toString()));
                                Long sub2 = Long.valueOf(CalcOmit.subExpect(varietyType, todayOmission.get(1).getExpect().toString(), todayOmission.get(2).getExpect().toString()) - 1);
                                sub = sub1 > sub2 ? sub1 : sub2;
                            } else {
                                Long sub1 = todayOmission.get(0).getExpect() - todayOmission.get(1).getExpect();
                                Long sub2 = todayOmission.get(1).getExpect() - todayOmission.get(2).getExpect() - 1;
                                sub = sub1 > sub2 ? sub1 : sub2;
                            }
                        }
                    }
                    subList.add(sub);
                }
            }
            //获取相减后最大的值
            count = Collections.max(subList);
        }
        return count;
    }

    /**
     * 合并list数据
     *
     * @param src
     * @param target
     * @return
     */
    public static List<Omission> MergeList(List<Omission> src, List<Omission> target) {
        List<Omission> returnList = new ArrayList<>();
        if(src.isEmpty() || src == null) {
            return target;
        }
        for (Omission source : src) {
            for (Omission tar : target) {
                //冠亚和
                if (source.getCrownSub() != null) {
                    if (source.getCrownSub().compareTo(tar.getCrownSub()) == 0) {
                        Omission omission = new Omission();
                        omission.setCrownSub(source.getCrownSub());
                        packageObject(source, tar, omission);
                        returnList.add(omission);
                    }
                }
                //冠亚和双面
                if (source.getCrownSubDesc() != null) {
                    if (source.getCrownSubDesc().equals(tar.getCrownSubDesc())) {
                        Omission omission = new Omission();
                        omission.setCrownSubDesc(source.getCrownSubDesc());
                        packageObject(source, tar, omission);
                        returnList.add(omission);
                    }
                }
                //号码遗漏的号码
                if (source.getNumberBall() != null) {
                    if (source.getNumberBall().equals(tar.getNumberBall())) {
                        Omission omission = new Omission();
                        omission.setNumberBall(source.getNumberBall());
                        packageObject(source, tar, omission);
                        returnList.add(omission);
                    }
                }
                //号码遗漏单双
                if (source.getSingleDouble() != null) {
                    if (source.getSingleDouble().equals(tar.getSingleDouble())) {
                        Omission omission = new Omission();
                        omission.setSingleDouble(source.getSingleDouble());
                        packageObject(source, tar, omission);
                        returnList.add(omission);
                    }
                }
                //号码遗漏大小
                if (source.getBigSmall() != null) {
                    if (source.getBigSmall().equals(tar.getBigSmall())) {
                        Omission omission = new Omission();
                        omission.setBigSmall(source.getBigSmall());
                        packageObject(source, tar, omission);
                        returnList.add(omission);
                    }
                }
            }
        }
        return returnList;
    }

    private static void packageObject(Omission source, Omission tar, Omission omission) {
        omission.setThisMonthOmi(source.getThisMonthOmi() == null ? tar.getThisMonthOmi() : source.getThisMonthOmi());
        omission.setThisWeekOmi(source.getThisWeekOmi() == null ? tar.getThisWeekOmi() : source.getThisWeekOmi());
        omission.setTodayAppear(source.getTodayAppear() == null ? tar.getTodayAppear() : source.getTodayAppear());
        omission.setTodayOmi(source.getTodayOmi() == null ? tar.getTodayOmi() : source.getTodayOmi());
        omission.setCurrentOmi(source.getCurrentOmi() == null ? tar.getCurrentOmi() : source.getCurrentOmi());
        omission.setHistoryOmi(source.getHistoryOmi() == null ? tar.getHistoryOmi() : source.getHistoryOmi());
    }

    public static List<Omission> getTwoFaceOmissions(List<Omission> todayOmission, List<String> twoFace, String dateType) {
        List<String> bigSmall = new ArrayList<>();
        List<String> singleDouble = new ArrayList<>();
        for (Omission omission : todayOmission) {
            bigSmall.add(omission.getBigSmall());
            singleDouble.add(omission.getSingleDouble());
        }
        todayOmission.clear();
        Map<String, Integer> bigSmallOmission = getTwoFaceMaxOmission(bigSmall);
        Map<String, Integer> singleDoubleOmission = getTwoFaceMaxOmission(singleDouble);
        for (String s : twoFace) {
            Omission currentOmission = new Omission();
            for (Map.Entry<String, Integer> entries : bigSmallOmission.entrySet()) {
                if (entries.getKey().equals(s)) {
                    currentOmission.setCrownSubDesc("冠亚和" + s);
                    if (dateType.equals("today")) {
                        currentOmission.setTodayOmi(entries.getValue().longValue());
                    }
                    if (dateType.equals("week")) {
                        currentOmission.setThisWeekOmi(entries.getValue().longValue());
                    }
                    if (dateType.equals("month")) {
                        currentOmission.setThisMonthOmi(entries.getValue().longValue());
                    }
                    if (dateType.equals("history")) {
                        currentOmission.setHistoryOmi(entries.getValue().longValue());
                    }
                }
            }
            for (Map.Entry<String, Integer> entries : singleDoubleOmission.entrySet()) {
                if (entries.getKey().equals(s)) {
                    currentOmission.setCrownSubDesc("冠亚和" + s);
                    if (dateType.equals("today")) {
                        currentOmission.setTodayOmi(entries.getValue().longValue());
                    }
                    if (dateType.equals("week")) {
                        currentOmission.setThisWeekOmi(entries.getValue().longValue());
                    }
                    if (dateType.equals("month")) {
                        currentOmission.setThisMonthOmi(entries.getValue().longValue());
                    }
                    if (dateType.equals("history")) {
                        currentOmission.setHistoryOmi(entries.getValue().longValue());
                    }
                }
            }
            todayOmission.add(currentOmission);
        }
        return todayOmission;
    }

    public static Map<String, Integer> getTwoFaceMaxOmission(List<String> params) {
        //String str = "大,小,小,小,大,小,小,大,小,小,小,大,大,大,大";
        //String str2 = "双,单,单,单,单";
        //List<String> strings = new ArrayList<>(Arrays.asList(str.split(",")));
        Map<String, Integer> resMap = new HashMap<>();
        int count = 1; //计算遗漏次数
        int theMaxOmission = 1; //最大遗漏
        int theSecondOmission = 1; //第二大遗漏
        String lastType = "";
        if (params.size() == 1) {
            String type = getType(params.get(0));
            resMap.put(type, 1);
            resMap.put(getType(type), 0);
        }
        for (int i = 0; i < params.size(); i++) {
            if (!((i + 1) == params.size())) {
                if (params.get(i).equals(params.get(i + 1))) {
                    count++;
                    //如果这次连续开出次数大于上一次的连续开出次数，则更新上次的最大次数
                    if (count > theMaxOmission) {
                        theMaxOmission = count;
                        String type = getType(params.get(i));
                        lastType = type;
                        resMap.put(type, theMaxOmission);
                    } else {
                        String type = getType(params.get(i));
                        //否则这次连续开出最大次数为另一面的最大次数
                        if (count > theSecondOmission) {
                            if (!type.equals(lastType)) {
                                theSecondOmission = count;
                                resMap.put(type, theSecondOmission);
                            }
                        }
                        if (!type.equals(lastType)) {
                            resMap.put(type, theSecondOmission);
                        } else {
                            resMap.put(getType(type), theSecondOmission);
                        }
                    }
                } else {
                    count = 1;
                    if (params.size() < 3) {
                        String type = getType(params.get(i));
                        resMap.put(type, count);
                        resMap.put(getType(type), count);
                    }
                    if (theMaxOmission == 1 && theSecondOmission == 1) {
                        String type = getType(params.get(i));
                        resMap.put(type, count);
                    }
                }
            }
        }
        if (resMap.size() < 2) {
            for (Map.Entry<String, Integer> entries : resMap.entrySet()) {
                resMap.put(getType(entries.getKey()), 1);
            }
        }
        System.out.println(resMap);
        return resMap;
    }

    /**
     * 验证程序，勿删
     *
     * @param args
     */
    public static void main(String[] args) {
        //String str = "双,单,双,单,单,单,单,双,单,单,单,单,双,单,单,单,单,单,双,单,单,单,单,单,单,单,单,单,单,单,单,单,双,单,双,单,双,双,单,双,单,单,单,单,双,双,双,双,双,双,单,单,双,单,单,双,双,双,双,双,双,单,单,双,单,单,双,单,单,单,双,单,双,单,双,单,单,双,单,单,双,单,单,双,双,双,双,双,单,双,单,单,双,单,双,单,双,单,双,单,双,双,单,单,单,单,双,双,单,单,单,双,双,单,双,双,单,双,单,单,单,单,双,双,单,单,双,单,单,单,单,单,单";
        String str = "大,大,大,大,大,小,小,大,小,大,大,小,小,大,小,大,大,小,小,大,小,大,小,小,小,大,小,小,小,小,小,大,小,小,小,大,大,大,小,大,小,小,小,大,小,小,大,大,小,大,大,小,小,大,小,大,大,小,大,大,小,小,大,小,大,大,大,小,小,大,大,小,大,小,大,小,大,小,大,小,小,大,大,大,小,大,大,大,大,大,大,小,大,小,大,大,小,大,小,大,大,大,大,大,小,小,大,小,大,小,小,小,小,小,小,大,大,小,小,小,小,大,大,小,小,小,大,大,小,小,大,小,小";
        //String str = "单,双,双";
        //String str = "大,大,大";
        List<String> params = new ArrayList<>(Arrays.asList(str.split(",")));
        Map<String, Integer> resMap = new HashMap<>();
        int count = 1; //计算遗漏次数
        int theMaxOmission = 1; //最大遗漏
        int theSecondOmission = 1; //第二大遗漏
        String lastType = "";
        if (params.size() == 1) {
            String type = getType(params.get(0));
            resMap.put(type, 1);
            resMap.put(getType(type), 0);
        }
        for (int i = 0; i < params.size(); i++) {
            if (!((i + 1) == params.size())) {
                if (params.get(i).equals(params.get(i + 1))) {
                    count++;
                    //如果这次连续开出次数大于上一次的连续开出次数，则更新上次的最大次数
                    if (count > theMaxOmission) {
                        theMaxOmission = count;
                        String type = getType(params.get(i));
                        lastType = type;
                        resMap.put(type, theMaxOmission);
                    } else {
                        String type = getType(params.get(i));
                        //否则这次连续开出最大次数为另一面的最大次数
                        if (count > theSecondOmission) {
                            if (!type.equals(lastType)) {
                                theSecondOmission = count;
                                resMap.put(type, theSecondOmission);
                            }
                        }
                        if (!type.equals(lastType)) {
                            resMap.put(type, theSecondOmission);
                        } else {
                            resMap.put(getType(type), theSecondOmission);
                        }
                    }
                } else {
                    count = 1;
                    if (params.size() < 3) {
                        String type = getType(params.get(i));
                        resMap.put(type, count);
                        resMap.put(getType(type), count);
                    }
                    if (theMaxOmission == 1 && theSecondOmission == 1) {
                        String type = getType(params.get(i));
                        resMap.put(type, count);
                    }
                }
            }
        }
        if (resMap.size() < 2) {
            for (Map.Entry<String, Integer> entries : resMap.entrySet()) {
                resMap.put(getType(entries.getKey()), 0);
            }
        }
        System.out.println(resMap);
        //System.out.println(doubleStr + "---" + theSecondOmission);
        //System.out.println(big + "---" + theMaxOmission);
        //System.out.println(small + "---" + theSecondOmission);
    }

    public static String getType(String str) {
        String type = "";
        if (str.equals("大") || str.equals("小")) {
            type = str.equals("大") ? "小" : "大";
        } else if (str.equals("单") || str.equals("双")){
            type = str.equals("单") ? "双" : "单";
        }else if (str.equals("龙") || str.equals("虎")){
            type = str.equals("龙") ? "龙" : "虎";
        }
        return type;
    }
}
