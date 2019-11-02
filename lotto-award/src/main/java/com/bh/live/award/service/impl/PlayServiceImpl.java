package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.PlayDao;
import com.bh.live.award.service.IPlayService;
import com.bh.live.model.lottery.Play;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 玩法表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class PlayServiceImpl extends ServiceImpl<PlayDao, Play> implements IPlayService {

    @Override
    public List<Play> selectBySeedNo(Integer seedNo) {
        QueryWrapper<Play> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Play::getSeedNo, seedNo);
        return list(queryWrapper);
    }
}
