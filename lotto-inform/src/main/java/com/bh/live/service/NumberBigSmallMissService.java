package com.bh.live.service;


import com.bh.live.model.inform.Omission;

import java.util.List;

/**
 * 号码遗漏功能
 *
 * @author Administrator
 * @title: INumberMissingService
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/25  16:30
 */
public interface NumberBigSmallMissService {

    /**
     * 获取冠亚和当前遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getCurrentOmission(Integer number, int varietyType);

    /**
     * 今日最大遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getTodayOmiss(Integer number, int varietyType);

    /**
     * 本周遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getThisWeekOmiss(Integer number, int varietyType);

    /**
     * 本月遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> getThisMonthOmiss(Integer number, int varietyType);

    /**
     * 今日出現次數
     *
     * @return
     */
    List<Omission> todayAppear(Integer number, int varietyType);

    /**
     * 获取历史遗漏
     *
     * @param varietyType
     * @return
     */
    List<Omission> historyMiss(Integer number, int varietyType);

    /**
     * 获取所有数据
     *
     * @param varietyType
     * @return
     */
    List<Omission> getOmissionCount(Integer number, int varietyType);
}
