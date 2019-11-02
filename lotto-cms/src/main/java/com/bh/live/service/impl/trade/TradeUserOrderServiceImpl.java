package com.bh.live.service.impl.trade;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.CopyUtil;
import com.bh.live.dao.trade.TradeUserOrderDao;
import com.bh.live.model.trade.TradeUserOrder;
import com.bh.live.model.trade.UserBuyOrder;
import com.bh.live.model.trade.vo.UserBuyOrderVO;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.UserBuyOrderExcelRes;
import com.bh.live.service.trade.ITradeUserOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户购买记录 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Service
public class TradeUserOrderServiceImpl extends ServiceImpl<TradeUserOrderDao, TradeUserOrder> implements ITradeUserOrderService {


    @Override
    public TableDataInfo selectUserByOrder(UserBuyOrderVO vo) {
        Page<UserBuyOrder> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        page.setRecords(baseMapper.selectUserByOrder(page,vo));
        return new TableDataInfo(page);
    }


    @Override
    public List<UserBuyOrderExcelRes> selectUserByOrderList(UserBuyOrderVO vo) {
        return CopyUtil.copyPropertiesList(UserBuyOrderExcelRes.class,baseMapper.selectUserByOrder(vo));
    }
}
