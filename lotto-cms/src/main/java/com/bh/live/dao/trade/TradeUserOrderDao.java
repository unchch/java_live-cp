package com.bh.live.dao.trade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.trade.TradeUserOrder;
import com.bh.live.model.trade.UserBuyOrder;
import com.bh.live.model.trade.vo.UserBuyOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户购买记录 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface TradeUserOrderDao extends BaseMapper<TradeUserOrder> {

    /**
     * 查询用户
     * @param page
     * @param param
     * @return
     */
    List<UserBuyOrder> selectUserByOrder(Page<UserBuyOrder> page,@Param("param") UserBuyOrderVO param);


    /**
     * 查询用户导出excel导出集
     * @param param
     * @return
     */
    List<UserBuyOrder> selectUserByOrder(@Param("param") UserBuyOrderVO param);

    /**
     * 查询用户购买几率条数
     * @param vo
     * @return
     */
    int selectOrderCount(UserBuyOrderVO vo);
}
