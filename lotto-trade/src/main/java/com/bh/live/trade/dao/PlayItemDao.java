package com.bh.live.trade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.lottery.PlayItem;
import com.bh.live.pojo.res.trade.ItemTabRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 投注项表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-29
 */
public interface PlayItemDao extends BaseMapper<PlayItem> {

    /**
     * 根据投注位查询投注项
     * @param bitNo
     * @return
     */
    List<ItemTabRes> selectItemTabRes(@Param("bitNo") String bitNo);
}
