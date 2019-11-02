package com.bh.live.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 * @title: DateUtil
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/26  20:17
 */
public class DateUtil {
    /**
     * 判断时间是否一周内
     *
     * @param addtime
     * @param now
     * @return
     */
    public static boolean isLatestWeek(Date addtime, Date now) {
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -7);  //设置为7天前
        Date before7days = calendar.getTime();   //得到7天前的时间
        if (before7days.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断时间是否在一月内
     *
     * @param addtime
     * @param now
     * @return
     */
    public static boolean isLatestMonth(Date addtime, Date now) {

        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -1);  //一月前前
        Date beforeOneMonth = calendar.getTime();   //得到一月前的时间
        if (beforeOneMonth.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断时间是否在两月内
     *
     * @param addtime
     * @param now
     * @return
     */
    public static boolean isTwoMonth(Date addtime, Date now) {

        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -2);  //一月前前
        Date beforeOneMonth = calendar.getTime();   //得到一月前的时间
        if (beforeOneMonth.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 转换成字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
  
    // 获得当天0点时间  
    public static Date getTimesmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, 5);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime();  
  
  
    }  
    // 获得昨天0点时间  
    public static Date getYesterdaymorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTimeInMillis(getTimesmorning().getTime()+3600*24*1000);  
        return cal.getTime();  
    }  
    // 获得当天近7天时间  
    public static Date getWeekFromNow() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTimeInMillis( getTimesmorning().getTime()-3600*24*1000*7);  
        return cal.getTime();  
    }  
  
    // 获得当天24点时间  
    public static Date getTimesnight() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, 24);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime();  
    }  
  
    // 获得本周一0点时间  
    public static Date getTimesWeekmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 5, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return cal.getTime();  
    }  
  
    // 获得本周日24点时间  
    public static Date getTimesWeeknight() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getTimesWeekmorning());  
        cal.add(Calendar.DAY_OF_WEEK, 7);  
        return cal.getTime();  
    }  
  
    // 获得本月第一天0点时间  
    public static Date getTimesMonthmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 05, 00, 00);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return cal.getTime();  
    }  
  
    // 获得本月最后一天24点时间  
    public static Date getTimesMonthnight() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 05, 00, 00);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
        cal.set(Calendar.HOUR_OF_DAY, 24);  
        return cal.getTime();  
    }  
  
    public static Date getLastMonthStartMorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getTimesMonthmorning());  
        cal.add(Calendar.MONTH, -1);  
        return cal.getTime();  
    }  
  
    public static Date getCurrentQuarterStartTime() {  
        Calendar c = Calendar.getInstance();  
        int currentMonth = c.get(Calendar.MONTH) + 1;  
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date now = null;  
        try {  
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 4);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
            }
            c.set(Calendar.DATE, 1);  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 当前季度的结束时间，即2012-03-31 23:59:59 
     * 
     * @return 
     */  
    public static Date getCurrentQuarterEndTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getCurrentQuarterStartTime());  
        cal.add(Calendar.MONTH, 3);  
        return cal.getTime();  
    }  
  
  
    public static Date getCurrentYearStartTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 5, 0, 0);  
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.YEAR));  
        return cal.getTime();  
    }  
  
    public static Date getCurrentYearEndTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getCurrentYearStartTime());  
        cal.add(Calendar.YEAR, 1);  
        return cal.getTime();  
    }  
  
    public static Date getLastYearStartTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getCurrentYearStartTime());  
        cal.add(Calendar.YEAR, 0);  
        return cal.getTime();  
    }  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        System.out.println("当天24点时间：getTimesnight " + getTimesnight().toLocaleString());  
        System.out.println("当前时间：" + new Date().toLocaleString());  
        System.out.println("当天0点时间：getTimesmorning " + getTimesmorning().toLocaleString());  
        System.out.println("昨天0点时间：getYesterdaymorning " + getYesterdaymorning().toLocaleString());  
        System.out.println("近7天时间：getWeekFromNow " + getWeekFromNow().toLocaleString());  
        System.out.println("本周周一0点时间：getTimesWeekmorning " + getTimesWeekmorning().toLocaleString());  
        System.out.println("本周周日24点时间：getTimesWeeknight " + getTimesWeeknight().toLocaleString());  
        System.out.println("本月初0点时间：getTimesMonthmorning " + getTimesMonthmorning().toLocaleString());  
        System.out.println("本月未24点时间：getTimesMonthnight " + getTimesMonthnight().toLocaleString());  
        System.out.println("上月初0点时间：getLastMonthStartMorning " + getLastMonthStartMorning().toLocaleString());  
        System.out.println("本季度开始点时间：getCurrentQuarterStartTime " + getCurrentQuarterStartTime().toLocaleString());  
        System.out.println("本季度结束点时间：getCurrentQuarterEndTime " + getCurrentQuarterEndTime().toLocaleString());  
        System.out.println("本年开始点时间：getCurrentYearStartTime " + getCurrentYearStartTime().toLocaleString());  
        System.out.println("本年结束点时间：getCurrentYearEndTime " + getCurrentYearEndTime().toLocaleString());  
        System.out.println("上年开始点时间：getLastYearStartTime " + getLastYearStartTime().toLocaleString());  
        System.out.println(1562533200191L-1562961840000L);
     
    } 
}