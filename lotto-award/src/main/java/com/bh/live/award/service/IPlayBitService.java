package com.bh.live.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.PlayBit;

import java.util.List;

/**
 * <p>
 * 投注位表 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface IPlayBitService extends IService<PlayBit> {
    /**
     *@description 查询投注位表
     *@author WuLong
     *@date 2019/8/6 16:13
     *@param playNos 玩法编号
     *@return List<PlayBit>
     */
    List<PlayBit> selectByPlayNo(List<String> playNos);
}
