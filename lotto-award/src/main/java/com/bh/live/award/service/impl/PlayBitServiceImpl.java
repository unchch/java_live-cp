package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.PlayBitDao;
import com.bh.live.award.service.IPlayBitService;
import com.bh.live.model.lottery.PlayBit;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 投注位表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class PlayBitServiceImpl extends ServiceImpl<PlayBitDao, PlayBit> implements IPlayBitService {

    @Override
    public List<PlayBit> selectByPlayNo(List<String> playNos) {
        QueryWrapper<PlayBit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(PlayBit::getPlayNo,playNos);
        return list(queryWrapper);
    }
}
