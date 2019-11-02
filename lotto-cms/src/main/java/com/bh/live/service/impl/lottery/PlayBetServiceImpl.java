package com.bh.live.service.impl.lottery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.lottery.PlayBetDao;
import com.bh.live.model.lottery.PlayBet;
import com.bh.live.service.lottery.IPlayBetService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 赔率基础表 服务实现类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@Service
public class PlayBetServiceImpl extends ServiceImpl<PlayBetDao, PlayBet> implements IPlayBetService {

    /**
     * 根据彩种编号查询玩法赔率信息，并分组
     *
     * @param seedNo
     * @return MAP: K:playNo, V: List<PlayBet>
     */
    @Override
    public Map<String, List<PlayBet>> selectPlayBetBySeedNo(Integer seedNo) {
        QueryWrapper<PlayBet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PlayBet::getSeedNo, seedNo);
        List<PlayBet> playBets = baseMapper.selectList(wrapper);
        if (CommonUtil.isNotEmpty(playBets)) {
            return playBets.stream().collect(Collectors.groupingBy(PlayBet::getPlayNo));
        }
        return Maps.newHashMap();
    }

}
