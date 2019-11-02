package com.bh.live.service.impl.trade;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CopyUtil;
import com.bh.live.dao.trade.OrderDao;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.OrderCount;
import com.bh.live.model.trade.OrderTotal;
import com.bh.live.model.trade.vo.OrderVO;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.OrderExcelRes;
import com.bh.live.service.trade.IOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-26
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements IOrderService {

    @Override
    public TableDataInfo selectOrder(OrderVO vo) {
        Page<Order> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        page.setRecords(baseMapper.selectOrder(page,vo));
        return new TableDataInfo(page);
    }

    @Override
    public OrderCount selectOrderCount(OrderVO vo) {
        List<OrderTotal> list = baseMapper.selectOrderCount(vo);
        OrderCount count = new OrderCount();
        count.setOrderTotal(list.stream().collect(Collectors.summingInt(OrderTotal::getTotal)));
        count.setAmountTotal(list.stream().collect(Collectors.summingInt(OrderTotal::getPayAmount)));
        count.setFirstUser(baseMapper.selectFirstUserBuy(vo));
        count.setRepeatUser(baseMapper.selectRepeatUserBuy(vo));
        count.setUserTotal(baseMapper.selectUserBuyCount(vo));
        return count;
    }

    @Override
    public List<OrderExcelRes> selectOrderList(OrderVO vo) {
        return CopyUtil.copyPropertiesList(OrderExcelRes.class,baseMapper.selectOrder(vo));
    }
}
