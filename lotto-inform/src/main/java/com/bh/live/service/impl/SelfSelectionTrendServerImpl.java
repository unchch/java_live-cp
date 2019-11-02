package com.bh.live.service.impl;

import com.bh.live.dao.SelfSelectionTrendDao;
import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.service.SelfSelectionTrendServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SelfSelectionTrendServerImpl implements SelfSelectionTrendServer {

    @Resource
    private SelfSelectionTrendDao selfSelectionTrendDao;

    @Override

    /**
     * 自选走势
     * @return
     */
    public List<StatHistoryDraw> querySelfSelectionTrend(Integer expect, Integer variety){
        return selfSelectionTrendDao.querySelfSelectionTrend(expect,variety);
    }
}
