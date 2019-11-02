package com.bh.live.service;

import java.util.Map;

/**
 * Create by Administrator ON 2019/7/23
 */
public interface AfcAndTrendService {

    /**
     * 亚冠和走势列表
     * @return
     */
    Map<String,Object> queryAfcAndTrendPage(Integer expect, int variety);

}
