package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.PlayBetDao;
import com.bh.live.award.service.IPlayBetService;
import com.bh.live.model.lottery.Play;
import com.bh.live.model.lottery.PlayBet;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 赔率基础表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class PlayBetServiceImpl extends ServiceImpl<PlayBetDao, PlayBet> implements IPlayBetService {

    @Override
    public List<PlayBet> selectBySeedNo(Integer seedNo) {
        QueryWrapper<PlayBet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlayBet::getSeedNo,seedNo);
        return list(queryWrapper);
    }
}
