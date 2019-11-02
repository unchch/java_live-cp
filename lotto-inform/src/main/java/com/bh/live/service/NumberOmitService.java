package com.bh.live.service;


import com.bh.live.model.inform.Omission;

import java.util.List;

/**
 * 号码遗漏
 *
 * @author mayn
 */

public interface NumberOmitService {

    /**
     * 获取冠亚和当前遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getCurrentOmission(int varietyType);

    /**
     * 今日最大遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getTodayOmiss(int varietyType);

    /**
     * 本周遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getThisWeekOmiss(int varietyType);

    /**
     * 本月遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getThisMonthOmiss(int varietyType);

    /**
     * 今日出現次數
     *
     * @return
     */
    List<Omission> todayAppear(int varietyType, String dateType);

    /**
     * 获取历史遗漏
     * @param varietyType
     * @return
     */
    List<Omission> historyMiss(int varietyType);

    /**
     * 获取所有数据
     * @param varietyType
     * @return
     */
    List<Omission> getOmissionCount(int varietyType);

}
