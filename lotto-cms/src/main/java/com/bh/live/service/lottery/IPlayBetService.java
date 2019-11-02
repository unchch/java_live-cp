package com.bh.live.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.PlayBet;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 赔率基础表 服务类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
public interface IPlayBetService extends IService<PlayBet> {
    /**
     * 根据彩种编号查询玩法赔率信息，并分组
     *
     * @param seedNo
     * @author: yq.
     * @return MAP: K:playNo, V: List<PlayBet>
     */
    Map<String, List<PlayBet>> selectPlayBetBySeedNo(Integer seedNo);
}
