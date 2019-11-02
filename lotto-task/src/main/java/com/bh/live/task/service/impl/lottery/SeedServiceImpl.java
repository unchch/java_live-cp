package com.bh.live.task.service.impl.lottery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.model.lottery.Seed;
import com.bh.live.task.dao.lottery.SeedDao;
import com.bh.live.task.service.lottery.ISeedService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 彩种表 服务实现类
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
@Service
@Slf4j
public class SeedServiceImpl extends ServiceImpl<SeedDao, Seed> implements ISeedService {

    /**
     * 查询所有彩种
     *
     * @return
     */
    @Override
    public List<Seed> selectAllSeed() {
        QueryWrapper<Seed> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Seed::getStatus, LotteryConstants.VALUE_0);
        wrapper.lambda().in(Seed::getSeedNo, Lists.newArrayList(302, 303));
        return baseMapper.selectList(wrapper);
    }
}
