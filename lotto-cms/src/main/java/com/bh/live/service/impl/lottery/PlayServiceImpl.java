package com.bh.live.service.impl.lottery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.lottery.PlayDao;
import com.bh.live.model.lottery.Play;
import com.bh.live.model.lottery.PlayBet;
import com.bh.live.pojo.req.lottery.PlayOddsReq;
import com.bh.live.pojo.res.lottery.PlayOddsRes;
import com.bh.live.service.lottery.IPlayBetService;
import com.bh.live.service.lottery.IPlayService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 玩法表 服务实现类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
@Service
public class PlayServiceImpl extends ServiceImpl<PlayDao, Play> implements IPlayService {

    @Autowired
    private IPlayBetService playBetService;

    /**
     * 查询玩法list
     *
     * @param play
     * @return
     */
    @Override
    public List<Play> selectPlayList(Play play) {
        if (null == play) {
            return null;
        }
        QueryWrapper<Play> wrapper = new QueryWrapper<>();
        if (null != play.getPlayNo()) {
            wrapper.lambda().eq(Play::getPlayNo, play.getPlayNo());
        }
        if (null != play.getSeedNo()) {
            wrapper.lambda().eq(Play::getSeedNo, play.getSeedNo());
        }
        if (null != play.getPlayMode()) {
            wrapper.lambda().eq(Play::getPlayMode, play.getPlayMode());
        }
        wrapper.orderByAsc("sort");
        return baseMapper.selectList(wrapper);
    }

    /**
     * 获得玩法及二级玩法赔率
     *
     * @param seedNo
     */
    @Override
    public Map<String, List<PlayOddsRes>> selectPlayOdds(Integer seedNo) {
        Map<String, List<PlayOddsRes>> ret = Maps.newHashMap();
        List<Play> plays = selectPlayList(new Play().setSeedNo(seedNo));
        if (CommonUtil.isEmpty(plays)) {
            return null;
        }

        // 根据彩种编号查询玩法赔率信息，并分组
        // @return MAP: K:playNo, V: List<PlayBet>
        Map<String, List<PlayBet>> betOddsByPlayNoMap = playBetService.selectPlayBetBySeedNo(seedNo);

        // K: playMode: 玩法模式（1：官方(official)  2：信用(credit)）
        Map<String, Integer> playModes = Maps.newHashMap();
        playModes.put("official", LotteryConstants.VALUE_1);
        playModes.put("credit", LotteryConstants.VALUE_2);

        playModes.forEach((k, v) -> {
            List<PlayOddsRes> oddsRes = Lists.newArrayList();
            plays.stream()
                    .filter((p) -> p.getPlayMode().compareTo(v) == 0)
                    .filter((p) -> p.getParentNo().equals(String.valueOf(LotteryConstants.VALUE_0)))
                    .forEach((p) -> {
                        PlayOddsRes res = new PlayOddsRes()
                                .setPlayNo(p.getPlayNo())
                                .setPlayName(p.getPlayName())
                                .setStatus(p.getStatus());
                        // 查询二级玩法
                        List<Play> subPlays = plays.stream()
                                .filter((sp) -> sp.getParentNo().equals(p.getPlayNo()))
                                .collect(Collectors.toList());
                        if (CommonUtil.isEmpty(subPlays)) {
                            subPlays = plays.stream()
                                    .filter((sp) -> sp.getPlayNo().equals(p.getPlayNo()))
                                    .collect(Collectors.toList());
                        }
                        subPlays.forEach((sp) -> {
                            // 填充投注项
                            if (CommonUtil.isEmpty(res.getSubOddsRes())) {
                                res.setSubOddsRes(Lists.newArrayList());
                            }
                            List<PlayBet> playBets = betOddsByPlayNoMap.get(sp.getPlayNo());
                            if (CommonUtil.isNotEmpty(playBets)) {
                                res.getSubOddsRes().addAll(playBets);
                            }
                        });
                        if (CommonUtil.isNotEmpty(res.getSubOddsRes())) {
                            oddsRes.add(res);
                        }
                    });
            ret.put(k, oddsRes);
        });
        return ret;
    }

    /**
     * 修改玩法赔率
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updatePlayOdds(Map<String, List<PlayOddsReq>> req) {
        List<PlayBet> all = Lists.newArrayList();
        req.forEach((k, v) ->
            v.stream()
                .filter((odds) -> CommonUtil.isNotEmpty(odds.getSubOddsRes()))
                .forEach((odds) -> {
                    all.addAll(odds.getSubOddsRes());
                }));
        return playBetService.updateBatchById(all);
    }
}