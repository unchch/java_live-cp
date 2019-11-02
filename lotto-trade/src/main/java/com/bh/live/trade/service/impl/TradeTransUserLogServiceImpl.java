package com.bh.live.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.pojo.req.trade.TradeTransUserLogReq;
import com.bh.live.trade.dao.TradeTransUserLogDao;
import com.bh.live.trade.service.ITradeTransUserLogService;
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
public class TradeTransUserLogServiceImpl extends ServiceImpl<TradeTransUserLogDao, TradeTransUserLogReq> implements ITradeTransUserLogService {

}
