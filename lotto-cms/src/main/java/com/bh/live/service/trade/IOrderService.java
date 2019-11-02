package com.bh.live.service.trade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderCount;
import com.bh.live.model.trade.vo.OrderVO;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.OrderExcelRes;

import java.util.List;


/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-26
 */
public interface IOrderService extends IService<Order> {

    /**
     * 查询全部用户竞猜记录
     * @param vo
     * @return
     */
    TableDataInfo selectOrder(OrderVO vo);

    /**
     * 查询全部用户竞猜记录
     * @param vo
     * @return
     */
    OrderCount selectOrderCount(OrderVO vo);


    /**
     * 查询全部用户竞猜记录
     * @param vo
     * @return
     */
    List<OrderExcelRes> selectOrderList(OrderVO vo);
}
