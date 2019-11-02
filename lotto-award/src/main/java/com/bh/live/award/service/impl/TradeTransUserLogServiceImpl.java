package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.TradeTransUserLogDao;
import com.bh.live.award.service.ITradeTransUserLogService;
import com.bh.live.model.trade.TradeTransUserLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户流水表日志表.记录交易日志 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class TradeTransUserLogServiceImpl extends ServiceImpl<TradeTransUserLogDao, TradeTransUserLog> implements ITradeTransUserLogService {

}
