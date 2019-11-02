package com.bh.live.dao.trade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderTotal;
import com.bh.live.model.trade.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-26
 */
public interface OrderDao extends BaseMapper<Order> {

    /**
     * 查询全部用户竞猜记录
     *
     * @param vo
     * @param page
     * @return
     */
    List<Order> selectOrder(IPage<Order> page, @Param("param") OrderVO vo);


    /**
     * 查询全部用户竞猜记录
     *
     * @param vo
     * @return
     */
    List<Order> selectOrder(@Param("param") OrderVO vo);


    /**
     * 统计竞猜数据
     *
     * @param vo
     * @return
     */
    List<OrderTotal> selectOrderCount(OrderVO vo);

    /**
     * 查询单日首次购买用户
     *
     * @param vo
     * @return
     */
    Integer selectFirstUserBuy(OrderVO vo);

    /**
     * 查询单日多次购买用户
     *
     * @param vo
     * @return
     */
    Integer selectRepeatUserBuy(OrderVO vo);

    /**
     * 查询单日总购买用户
     *
     * @param vo
     * @return
     */
    Integer selectUserBuyCount(OrderVO vo);

}
