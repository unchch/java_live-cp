package com.bh.live.common.helper;/**
 * Create by Administrator ON 2019/7/4
 */

import com.bh.live.common.TrendUtils.CalcOmit;
import com.bh.live.model.inform.Omission;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2019/7/4 19:56
 * @desc
 * @Version 1.0
 */
public class CurrentOmissionHelper {

    /**
     * 计算当前遗漏
     *
     * @param varietyType
     * @param key
     * @param omissions
     * @param topExpect
     * @return
     */
    public static List<Omission> getCurrentOmission(int varietyType, Integer key, List<Omission> omissions, Omission topExpect) {
        List<Omission> returnList = new ArrayList<>();
        Long count = AllOmissionHelper.getOmissions(NumberHandlerHelper.getFieldValue(topExpect, key, "numberBall"), key.toString(), varietyType, topExpect, omissions);
        /*if (!(topExpect.getNumberBall().compareTo(key) == 0)) {
            //如果最新一期不是当前冠亚和，开出当前冠亚和的最新一期减去最新一期的值为当前遗漏
            if (varietyType == 1) {
                count = topExpect.getExpect() - secondExpect.getExpect();
            } else {
                count = Long.valueOf(CalcOmit.subExpect(varietyType, topExpect.getExpect().toString(), secondExpect.getExpect().toString()));
            }
        } else {
            //如果是，返回-1
            count += -1;
            for (int i = 0; i < omissions.size(); i++) {
                //判断期数是否是连续开出当前冠亚和
                if (!((i + 1) == omissions.size())) {
                    if (varietyType == 1) {
                        if (topExpect.getExpect() - i == omissions.get(i + 1).getExpect()) {
                            //如果是，继续加-1
                            count += -1;
                        }
                    } else {
                        if (CalcOmit.subExpect(varietyType, topExpect.getExpect().toString(), omissions.get(i + 1).getExpect().toString()) == 0) {
                            //如果是，继续加-1
                            count += -1;
                        }
                    }
                }
            }
        }*/
        Omission currentOmission = new Omission();
        currentOmission.setNumberBall(key);
        currentOmission.setCurrentOmi(count);
        returnList.add(currentOmission);
        return returnList;
    }

    /**
     * 获取计算后的今日，本周，本月遗漏数据
     *
     * @param varietyType
     * @param mission
     * @param dateType
     * @param map
     * @param numbers
     * @return
     */
    public static List<Omission> getPackageOmissionCount(int varietyType,
                                                         List<Omission> mission, String dateType,
                                                         Map<Integer, List<Omission>> map, List numbers) {
        Map<Integer, List<Omission>> collect = mission.stream().collect(Collectors.groupingBy(Omission::getNumberBall));
        Map<Object, List<Omission>> resMap = new HashMap<>();
        long millis = System.currentTimeMillis();
        for (Object number : numbers) {
            List<Omission> list = new ArrayList<>();
            list.add(mission.get(0)); //第一条数据必须放，
            for (Map.Entry<Integer, List<Omission>> entry : collect.entrySet()) {
                if (entry.getKey() == number) {
                    list.addAll(entry.getValue());
                }
            }
            /*//放入当前冠亚和到list
            for (Omission omission : mission) {
                if (omission.getNumberBall().compareTo(Integer.valueOf(number.toString())) == 0) {
                    list.add(omission);
                }
            }*/
            //查询上一个日期最新的一条数据
            if (dateType.equals("week") || dateType.equals("month")) {
                list.addAll(map.get(Integer.valueOf(number.toString())));
            } else {
                list.add(mission.get(mission.size() - 1)); //今日的最后一条数据必须放
            }
            resMap.put(number, list);
        }
        System.out.println("list 分组结束，用时：" + (System.currentTimeMillis() - millis));
        millis = System.currentTimeMillis();
        mission.clear();
        for (Map.Entry<Object, List<Omission>> entry : resMap.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            Long omissionCount = packageOmissionCount(entry.getValue(), varietyType);
            System.out.println("计算" + entry.getKey() + "遗漏结束:用时" + (System.currentTimeMillis() - millis));
            Omission omission = new Omission();
            omission.setNumberBall(Integer.valueOf(entry.getKey().toString()));
            if (dateType.equals("today")) {
                omission.setTodayOmi(omissionCount);
            }
            if (dateType.equals("week")) {
                omission.setThisWeekOmi(omissionCount);
            }
            if (dateType.equals("month")) {
                omission.setThisMonthOmi(omissionCount);
            }
            if (dateType.equals("history")) {
                omission.setHistoryOmi(omissionCount);
            }
            mission.add(omission);
        }
        return mission;
    }

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
                            if (todayOmission.get(i).getNumberBall().compareTo(todayOmission.get(i + 1).getNumberBall()) == 0) {
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
                        } else if (!(todayOmission.get(0).getNumberBall().compareTo(todayOmission.get(1).getNumberBall()) == 0)
                                && !(todayOmission.get(1).getNumberBall().compareTo(todayOmission.get(2).getNumberBall()) == 0)) {
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
                        } else if ((todayOmission.get(0).getNumberBall().compareTo(todayOmission.get(1).getNumberBall()) == 0)
                                && !(todayOmission.get(1).getNumberBall().compareTo(todayOmission.get(2).getNumberBall()) == 0)) {
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
}
