package com.bh.live.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JudgeDateUtil
 * @description: 判断预播日期工具类
 * @author: Morphon
 * @date 2019-08-12
 */
public class JudgeDateUtil {

    //判断今天周几，是否大于今天,不大于的返回false
    public static Map<Integer, Boolean> judge(String liveDate, String openTime) {
        Map<Integer, Boolean> resMap = new HashMap<>();
        String[] split = liveDate.split(",");
        for (String s : split) {
            resMap.put(Integer.valueOf(s), isTrue(openTime, s));
        }
        return resMap;
    }

    public static boolean isTrue(String openTime, String s) {
        int currentDay = DateUtils.dayForWeek(new Date()); //当前星期几
        String nowTime = DateUtils.formatDateTime(new Date()).split(" ")[1].substring(0, 5); //当前时分
        int i = Integer.valueOf(s) - 1; //减1是为了得到实际的星期几1代表周日，7代表周六，所以减1
        if (i == 0 && currentDay == 0) {  //周日为0，最大，同是周日时判断时间
            if (openTime.compareTo(nowTime) == 1) { //字符串对比，1表示大于
                return true;
            } else {
                return false;
            }
        }
        if (i == 0 && i < currentDay) {  //周日为0最大直接返回true
            return true;
        }
        if (i > currentDay) { //其他时间正常判断大小
            return true;
        } else if (i == currentDay) {
            if (openTime.compareTo(nowTime) == 1) { //字符串对比，1表示大于
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        String str1 = "03:30";
        String str2 = "03:29";
        System.out.println(str1.compareTo(str2));
    }
}
