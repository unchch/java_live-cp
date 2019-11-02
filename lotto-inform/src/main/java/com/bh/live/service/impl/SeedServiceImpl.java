package com.bh.live.service.impl;

import com.bh.live.dao.SeedDao;
import com.bh.live.pojo.res.lottery.SeedNavRes;
import com.bh.live.service.SeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 彩种分类 服务实现类
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
@Service
@Slf4j
public class SeedServiceImpl implements SeedService {

    @Autowired
    private SeedDao seedDao;


    @Override
    public List<SeedNavRes> selectSeed() {
        return seedDao.selectSeed();
    }
}
