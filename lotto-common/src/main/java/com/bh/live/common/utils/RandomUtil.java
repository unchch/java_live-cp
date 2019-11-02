package com.bh.live.common.utils;

import java.util.Calendar;
import java.util.Random;

/**
 * @author yq.
 * @ClassName RandomUtil
 * @description RandomUtil
 * @date 2019-08-15 14:15:00
 */
public class RandomUtil {

    /**
     * 房间随机人数-默认基数
     */
    private static final int DEFAULT_BASE = 100;

    /**
     * 房间随机人数产生工具函数
     *
     * @return 房间人数
     */
    public static int random() {
        return random(1, DEFAULT_BASE, 1);
    }

    /**
     * 房间随机人数产生工具函数
     *
     * @param index 房间序号
     * @param size  房间数量
     * @return 房间人数
     */
    public static int random(int index, int size) {
        return random(index, DEFAULT_BASE, size);
    }

    /**
     * 房间随机人数产生工具函数
     *
     * @param index 房间序号
     * @param base  基数
     * @param size  房间数量
     * @return 房间人数
     */
    public static int random(int index, int base, int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.getNowDate());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return random(hour, minute, second, index, base, size);
    }

    /**
     * 房间随机人数产生工具函数
     *
     * @param hour   当前时间
     * @param minute 当前分钟
     * @param second 当前秒
     * @param index  房间序号
     * @param base   基数
     * @param size   房间数量
     * @return 房间人数
     */
    public static int random(int hour, int minute, int second, int index, int base, int size) {
        index = index + 1;
        hour = hour == 0 ? hour + 1 : hour;
        int s = second / 2 < 0 ? 1 : second / 2;
        int defaultNum = hour * base + minute + s;
        int deducting = defaultNum / 3 * index / size;
        return defaultNum - deducting + new Random().nextInt(base + size - index);
    }
}
