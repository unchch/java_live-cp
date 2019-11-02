package com.bh.live.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYYMM = "yyyyMM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTimeForYYYYMM() {
        Date now = new Date();
        return DateFormatUtils.format(now, YYYYMM);
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

    /**
     * 转换成字符串
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(date);
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 判断两个时间是否同一天
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameDay(Date d1, Date d2) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(d1, d2);
    }

    /**
     * 将LocalDateTime 转换为Date 对象，包含日期(不包含时间)
     *
     * @param localDate
     * @return
     */
    public static Date convertLocalDateToDate(LocalDate localDate) {
        ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.systemDefault());
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /***
     * 获取当日00:00:00以及23:59:59时间
     * @param date
     * @return
     */
    public static Map<String, String> getBeginAndEndTiem(Date date) {
        Map<String, String> map = new HashMap<>();
        String formatDate = formatDate(date);
        String beginTime = formatDate + " 00:00:00";
        String endTime = formatDate + " 23:59:59";
        map.put("begin", beginTime);
        map.put("end", endTime);
        return map;
    }

    /***
     * 获取时间 格式为18:10
     * @param now
     * @return
     */
    public static StringBuffer getTime(LocalDateTime now) {
        // 构造 08:12 结构
        StringBuffer time = new StringBuffer();
        int hour = now.getHour();
        int min = now.getMinute();
        if (hour < 10) {
            time.append("0" + hour);
        } else {
            time.append("" + hour);
        }
        time.append(":");
        if (min < 10) {
            time.append("0" + min);
        } else {
            time.append("" + min);
        }
        return time;
    }

    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getBeforeDate() {
        Calendar calendar = Calendar.getInstance();
        //得到前一天
        calendar.add(Calendar.DATE, 0);
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔 返回天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 比较两个时间大小
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compare_to(Date date1, Date date2) {
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        if (date1.getTime() > date2.getTime()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取日期格式为06/10
     *
     * @param date
     * @return
     */
    public static String getStringDate(Date date) {
        String formatDate = formatDate(date);
        String[] split = formatDate.split("-");
        return split[1] + "/" + split[2];
    }

    public static Map<String, Object> getBeginDayAndEndDay() {
        Map<String, Object> resMap = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        System.out.println("===============本月first day:" + first);

        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        System.out.println("===============本月last day:" + last);
        resMap.put("firstDay", first);
        resMap.put("lastDay", last);
        return resMap;
    }
}
