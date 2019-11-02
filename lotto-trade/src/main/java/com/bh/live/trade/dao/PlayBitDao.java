package com.bh.live.trade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.lottery.PlayBit;
import com.bh.live.pojo.res.trade.BitTabRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 投注位表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-29
 */
public interface PlayBitDao extends BaseMapper<PlayBit> {

    /**
     * 根据玩法查询投注位
     * @param playNo
     * @return
     */
    List<BitTabRes> selectBitTabRes(@Param("playNo") String playNo);
}
