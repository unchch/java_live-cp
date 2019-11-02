package com.bh.live.trade.service;

import java.util.Map;

/**
 * @author lgs
 * @title: IBetTabService
 * @projectName java_live-cp
 * @description: 竞猜 发布竞猜tab项
 * @date 2019/8/6  20:08
 */
public interface IBetTabService {

    /**
     * 查询彩种竞猜选项
     * @param seedNo
     * @return
     */
    Map<String, Object> selectTab(Integer seedNo);
}
