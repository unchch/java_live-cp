package com.bh.live.service.trade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.trade.TradeUserOrder;
import com.bh.live.model.trade.vo.UserBuyOrderVO;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.trade.UserBuyOrderExcelRes;

import java.util.List;

/**
 * <p>
 * 用户购买记录 服务类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
public interface ITradeUserOrderService extends IService<TradeUserOrder> {

    /**
     * 查询用户
     * @param vo
     * @return
     */
    TableDataInfo selectUserByOrder(UserBuyOrderVO vo);

    /**
     * 查询导出结果集
     * @param vo
     * @return
     */
    List<UserBuyOrderExcelRes> selectUserByOrderList(UserBuyOrderVO vo);
}
