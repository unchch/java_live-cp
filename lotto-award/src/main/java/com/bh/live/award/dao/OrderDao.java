package com.bh.live.award.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.trade.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface OrderDao extends BaseMapper<Order> {
    /**
     * @param seedNo  彩种ID
     * @param issueNo 彩种期号
     * @param status  订单状态集合
     * @return List<String>  list订单
     * @description 查询订单集合
     * @author WuLong
     * @date 2019/8/9 14:25
     */
    List<String> selectDrawCodeOrderNos(@Param("seedNo") Integer seedNo, @Param("issueNo") String issueNo, @Param("status") List<Integer> status);

    /**
     * @param orders 订单编号list
     * @return List<Order>
     * @description 查询订单表并关联查询出订单投注表
     * @author WuLong
     * @date 2019/8/9 14:36
     */
    List<Order> selectOrderAndOrderInfo(@Param("orders") List<String> orders);

}
