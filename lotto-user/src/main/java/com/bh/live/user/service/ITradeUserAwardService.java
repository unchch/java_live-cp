package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.trade.TradeUserAward;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeUserAwardReq;
import com.bh.live.pojo.res.live.ChatUser;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.UserAwardtRankingRes;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 用户打赏记录 服务类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
public interface ITradeUserAwardService extends IService<TradeUserAward> {
    /**
     * 新增用户打赏记录
     * @param req
     * @return
     */
    Result add(TradeUserAwardReq req, ChatUser user, LiveUser u);

    /**
     * 用户打赏记录列表
     * @param roomId
     * @return
     */
    TableDataInfo getList(Integer roomId,Integer pageNum,Integer pageSize, Date  startTime, Date endTime);

    /**
     * 用户打赏榜单
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserAwardtRankingRes> getRankingList(Integer roomId, Date  startTime, Date endTime);
}
