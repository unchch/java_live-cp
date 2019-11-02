package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.anchor.LiveSeed;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.res.live.LiveRoomSeedRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 直播彩种管理 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
public interface LiveSeedDao extends BaseMapper<LiveSeed> {

    /**
     * 根据id查询彩种
     *
     * @param ids
     * @return
     */
    List<Seed> getBySeedNos(@Param("ids") List<Integer> ids);

    /**
     * 查询直播彩种用户信息
     *
     * @param seedNo
     * @param queryType
     * @return
     */
    List<LiveRoomSeedRes> getLiveUserBySeed(@Param("seedNo") Integer seedNo, @Param("queryType") String queryType);

}
