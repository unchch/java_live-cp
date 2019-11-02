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
import java.util.*;

/**
 * 时间工具类
 *
 * @author yq.
 * @version 1.0.0 2019-06-12
 * @since 1.0.0 2019-06-12
 **/
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYYMM = "yyyyMM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String HH_MM_SS = "HH:mm:ss";

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
        try{
            return sdf.format(date);
        }catch (Exception e){
            throw new RuntimeException("method formatDate error: yyyy-MM-dd", e);
        }
    }

    /**
     * 转换成字符串
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try{
            return sdf.format(date);
        }catch (Exception e){
            throw new RuntimeException("method formatDateTime error: YYYY_MM_DD_HH_MM_SS", e);
        }
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), YYYY_MM_DD_HH_MM_SS);
        } catch (ParseException e) {
            throw new RuntimeException("method parseDate error: YYYY_MM_DD_HH_MM_SS", e);
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
    public static long getDatePoor(Date endDate, Date nowDate, String flag) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异.
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        if (flag.equals("day")) {
            return day;
        }
        // 计算差多少小时
        long hour = diff % nd / nh + day * 24;
        if (flag.equals("hour")) {
            return hour;
        }
        // 计算差多少分钟
        long min = diff % nd % nh / nm + hour * 60;
        if (flag.equals("min")) {
            String s = sdf.format(endDate).split(" ")[1].split(":")[2];
            String s1 = sdf.format(nowDate).split(" ")[1].split(":")[2];
            if (s.equals(s1)) {
                return min;
            }
            if (Integer.valueOf(s) > Integer.valueOf(s1)) {
                return min;
            }
            if (Integer.valueOf(s) < Integer.valueOf(s1)) {
                return min + 1;
            }
        }
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return diff;
    }

    public static String timeSubtraction(String time1, String time2) throws ParseException {
        /**
         *@description time2 是大的时间
         *@param  [time1, time2]
         *@return java.lang.String
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        long newTime1 = simpleDateFormat.parse(time2).getTime();
        long newTime2 = simpleDateFormat.parse(time1).getTime();

        //获取两时间相差的毫秒数
        Long result = newTime1 - newTime2;
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;


        long hour = result % nd / nh;     //获取相差的小时数
        //获取相差的分钟数
        long min = result % nd % nh / nm;
        long second = result % nd % nh % nm / ns;
        long day = result / nd;

        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");//初始化Formatter的转换格式。
        long mMiles = min * 60000;    //分钟数转换成毫秒
        long mSeconds = second * 1000;    //秒转换成毫秒
        long resulMiles = (mMiles + mSeconds);

        //下面这段很重要 ,计算之后设置时区,不然会差几小时
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String resultFormat = formatter.format(resulMiles);
        //我这段是在一天内计算的 如果大于一天 就把下面的 day*24加到小时上就可以了
        return resultFormat + "," + day;
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
    public static Map<String, String> getBeginAndEndTime(Date date) {
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
     * @param bigTime   大的时间
     * @param smallTime 小的时间
     * @return int
     * @throws
     * @description 计算2个时间相差多少秒
     * @author WuLong
     * @date 2019/7/26 17:06
     */
    public static int differentSeconds(Date bigTime, Date smallTime) {
        return (int) ((bigTime.getTime() - smallTime.getTime()) / 1000);
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

    public static String addTime(String timeStr, String addnumber) {
        String str = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(timeStr);
            //时间累计
            Calendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.MINUTE, Integer.parseInt(addnumber));
            str = df.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean isBetweenTime(Date begin, Date end, Date giveTime) {
        long give = giveTime.getTime();
        long beginTime = begin.getTime();
        long endTime = end.getTime();
        if (give >= beginTime && give <= endTime) {
            return true;
        }
        return Boolean.FALSE;
    }

    // 获取日期    把日期往后增加一天.整数往后推,负数往前移动
    public static String getDay(int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();// 取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
        return formatter.format(calendar.getTime());
    }

    //获取日期时间
    public static String getDayTime(Date date, int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
        return formatter.format(calendar.getTime());
    }

    //获取当前星期
    public static int dayForWeek(Date changeDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(changeDate);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }


    public static Date getDate(String datetime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据传递的分钟数获取几小时几分钟
     *
     * @param min
     * @return
     */
    public static String calTime(long min) {
        if (min < 60) {
            return min + "分钟";
        } else {
            if (min % 60 == 0) {
                return min / 60 + "小时";
            } else {
                return min / 60 + "小时" + min % 60 + "分钟";
            }
        }
    }


    public static boolean isIinYesterday(Date cuurent) {

        return isBetweenTime(getDate(getDay(-1) + " 00:00:00"), getDate(getDay(0) + " 00:00:00"), cuurent);
    }


    /**
     * 获得彩期开盘时间
     *
     * @param argsDate
     * @param createDate
     * @param interval   彩期间隔
     * @param number     距离第一期期号
     * @return
     */
    public static Date getIssueStartTime(Date argsDate, Date createDate, int interval, Integer number) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(argsDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createDate);
        calendar.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, cal.get(Calendar.SECOND));

        interval *= number;
        calendar.add(Calendar.SECOND, interval);
        return calendar.getTime();
    }

    /**
     * 获得彩期开奖时间
     *
     * @param startTime
     * @param interval
     * @return
     */
    public static Date getIssueOpenTime(Date startTime, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.SECOND, interval);
        return calendar.getTime();
    }

    /**
     * 获得彩期封盘时间和停售时间
     *
     * @param openTime
     * @param closingSeconds
     * @return
     */
    public static Date getIssueClosingTime(Date openTime, int closingSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(openTime);
        calendar.add(Calendar.SECOND, closingSeconds * -1);
        return calendar.getTime();
    }

    /**
     * 判断两个时间时分秒的大小
     *
     * @param beginTime
     * @param endTime
     * @return true: 后面时间更大，false: 前面时间更大
     */
    public static boolean timeHHMMDD(Date beginTime, Date endTime) {
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (begin.get(Calendar.HOUR_OF_DAY) < end.get(Calendar.HOUR_OF_DAY)) {
            return true;
        } else if (begin.get(Calendar.HOUR_OF_DAY) == end.get(Calendar.HOUR_OF_DAY)) {
            if (begin.get(Calendar.MINUTE) < end.get(Calendar.MINUTE)) {
                return true;
            } else if (begin.get(Calendar.MINUTE) == end.get(Calendar.MINUTE)) {
                if (begin.get(Calendar.SECOND) < end.get(Calendar.SECOND)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 比较:比较时间是否相同
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqual(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }

        return date1 != null && date2 != null && date1.getTime() == date2.getTime();
    }

    /**
     * 比较两个日期之间相差多少秒
     */
    public static Long compareSecond(Calendar c1, Calendar c2) {
        assert (c1 != null);
        assert (c2 != null);
        return (c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000;
    }

    /**
     * 获取到当前时间的时间戳
     *
     * @param date
     * @return
     * @author yq.
     */
    public static long getTimestampByNow(Date date) {
        long timestamp = date.getTime() - System.currentTimeMillis();
        if (timestamp < 0) {
            return 0;
        }
        return timestamp;
    }

    /**
     * 根据Date获取Calender
     *
     * @param date
     * @return
     */
    public static Calendar getCalenderByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static void main(String[] args) {
        System.out.println(dayForWeek(new Date()));
    }
}
