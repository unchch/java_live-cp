package com.bh.live.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.PlayItem;

import java.util.List;

/**
 * <p>
 * 投注项表 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface IPlayItemService extends IService<PlayItem> {
    /**
     * 根据投注位编号查询投注项
     * @param bitNos 注位编号集合
     * @return List<PlayItem>
     */
    List<PlayItem> selectByBitNo(List<String> bitNos);
}
