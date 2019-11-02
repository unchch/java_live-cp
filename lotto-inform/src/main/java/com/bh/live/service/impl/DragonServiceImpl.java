package com.bh.live.service.impl;

import com.bh.live.dao.DragonDao;
import com.bh.live.service.DragonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DragonServiceImpl implements DragonService {

    @Resource
    private DragonDao dragonDao;

    @Override
    public void insertDragon(Map<String, Object> param) {
        dragonDao.insertDragon(param);
    }
}
