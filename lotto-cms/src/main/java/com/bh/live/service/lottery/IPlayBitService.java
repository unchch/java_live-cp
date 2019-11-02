package com.bh.live.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.PlayBit;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 投注位表 服务类
 * </p>
 *
 * @author: yq.
 * @since 2019-07-23
 */
public interface IPlayBitService extends IService<PlayBit> {

    /**
     * 根据玩法查询投注位
     * @param playNos
     * @param status
     * @author: yq.
     * @return
     */
    List<PlayBit> selectByPlayNos(Collection<String> playNos, Integer status);
}
