package com.bh.live.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.trade.Order;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.order.OrderReq;
import com.bh.live.pojo.req.trade.OrderFullReq;
import com.bh.live.pojo.req.trade.OrderHistoryFullReq;
import com.bh.live.pojo.req.trade.ProfitRateRankReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-17
 */
public interface IOrderService extends IService<Order> {

    /**
     * 新增订单
     * @auth lgs
     * @param vo
     * @return
     */
    public Result<OrderRes> insert(OrderReq vo, LiveUser liveUser);

    /**
     * 根据用户id查询历史订单返回信息
     * @auth lgs
     * @param req
     * @return
     */
    public TableDataInfo queryOrderListHistoryByUserId(OrderHistoryFullReq req);

    /**
     * 查询竞猜列表
     *
     * @return
     */
    public TableDataInfo queryOrderList(OrderFullReq req);

    /**
     * 查询用户开奖统计信息 当前连赢 指标 10期 20期 30期 50期 今日 昨日 赢率 盈利
     *
     * @return
     */
    public UserOrderStatisticsRes queryUserOrderStatistics(Integer userId);


    /**
     * 查询是否已经购买订单
     *
     * @param vo
     * @return
     */
    Integer selectCount(OrderReq vo, LiveUser liveUser);

    /**
     * 查询竞猜方案（未开奖）
     * @author Morphon
     * @param seedNo
     * @param lotIssueNo
     * @return
     */
    List<GuessingRes> selectGuess(Integer seedNo, String lotIssueNo);

    /**
     * 竞猜方案详情
     * @author Morphon
     * @param id
     * @return
     */
    GuessingDetailRes getGuessDetail(Integer id,Integer userId);

    /**
     * 查询订单详情
     * @param order
     * @return
     */
    List<OrderInfoContentRes> selectOrderInfo(Order order);

    /**
     * 查询竞猜排行榜
     * @param req
     * @return
     */
    List<ProfitRateRankRes> selectRateRank(ProfitRateRankReq req);

    /**
     * 根据彩种获取玩法编号
     * @param seedNo
     * @return
     */
    List<LottoPlayNoNameRes> queryPlayNoAndNameBySeedNo(Integer seedNo);

}
