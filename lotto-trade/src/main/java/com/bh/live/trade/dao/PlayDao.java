package com.bh.live.trade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.lottery.Play;
import com.bh.live.pojo.res.trade.PlayTabRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 玩法表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-29
 */
public interface PlayDao extends BaseMapper<Play> {

    /**
     * 根据彩种查询玩法
     * @param seedNo
     * @return
     */
    List<PlayTabRes> selectPlayRes(@Param("seedNo") Integer seedNo);
}
