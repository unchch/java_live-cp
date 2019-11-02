package com.bh.live.service.impl.trade;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.dao.trade.TradeTransUserLogDao;
import com.bh.live.model.trade.TradeTransUserLog;
import com.bh.live.service.trade.ITradeTransUserLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户流水表日志表.记录交易日志 服务实现类
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Service
public class TradeTransUserLogServiceImpl extends ServiceImpl<TradeTransUserLogDao, TradeTransUserLog> implements ITradeTransUserLogService {

}
