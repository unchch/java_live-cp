package com.bh.live.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.trade.TradeUserAward;
import com.bh.live.pojo.req.trade.TradeUserAwardReq;
import com.bh.live.pojo.res.live.ChatGift;
import com.bh.live.pojo.res.trade.TradeUserAwardRes;
import com.bh.live.pojo.res.trade.UserAwardtRankingRes;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户打赏记录 Mapper 接口
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
public interface TradeUserAwardDao extends BaseMapper<TradeUserAward> {

    List<TradeUserAwardRes> queryTradePage(Page<TradeUserAwardRes> page, @Param("roomId") Integer roomId,
                                           @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<UserAwardtRankingRes> queryRanking(@Param("roomId") Integer roomId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    ChatGift getGift(@Param("giftId") Integer giftId);
}
