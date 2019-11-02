package com.bh.live.trade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.trade.Order;
import com.bh.live.pojo.req.trade.OrderFullReq;
import com.bh.live.pojo.req.trade.OrderHistoryFullReq;
import com.bh.live.pojo.req.trade.ProfitRateRankReq;
import com.bh.live.pojo.res.trade.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-17
 */
public interface OrderDao extends BaseMapper<Order> {

    /**
     * 查询订单列表
     */
    public List<OrderFullRes> queryOrderList(Page<OrderFullRes> iPage , @Param("req") OrderFullReq req);

    /**
     * 查询订单列表大小
     * @param req
     * @return
     */
    public Integer queryOrderListSize(@Param("req") OrderFullReq req);

    /**
     * @return
     * @author Morphon
     * 根据彩种号和彩期号获取未开奖的竞猜列表
     */
    List<GuessingRes> guessingList(@Param("seedNo") Integer seedNo, @Param("lotIssueNo") String lotIssueNo);

    /**
     * 查询竞猜详情
     *
     * @param id
     * @return
     * @author Morphon
     */
    GuessingDetailRes guessingDetail(@Param("id") Integer id, @Param("userId") Integer userId);

    /**
     * 查询用户盈利率排行榜
     *
     * @param req
     * @return
     */
    List<ProfitRateRankRes> selectProfitRate(ProfitRateRankReq req);

    /**
     * 查询用户赢率排行榜
     *
     * @param req
     * @return
     */
    List<ProfitRateRankRes> selectUserWinRate(ProfitRateRankReq req);

    /**
     * 查询用户连赢
     * @param req
     * @return
     */
    List<ProfitRateRankRes> selectUserJoinWin(ProfitRateRankReq req);

    /**
     * 查询近10期结果
     * @param userId
     * @return
     */
    public String queryRecentResultByTen(@Param("userId") Integer userId);

    /**
     * 查询用户本期是否已经发单
     * @param userIds
     * @return
     */
    List<Integer> selectUserPush(@Param("userIds") String userIds,@Param("seedNo") Integer seedNo);

    /**
     * 根据彩种查询玩法
     * @param seedNo
     * @return
     */
    List<LottoPlayNoNameRes> queryPlayNoAndNameBySeedNo(@Param("seedNo") Integer seedNo);

    /**
     * 查询订单历史流水
     * @param iPage
     * @param req
     * @return
     */
    public List<HistoryOrderRes> queryOrderListHistoryByUserId(Page<HistoryOrderRes> iPage,@Param("req") OrderHistoryFullReq req);

}
