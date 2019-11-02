package com.bh.live.trade.service;

import com.bh.live.common.enums.trade.TransEnum;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.trade.TradeSerialNumReq;

import java.math.BigDecimal;

/**
 * @author lgs
 * @title: IUserTradeService
 * @projectName java_live-cp
 * @description: 用户交易服务
 * @date 2019/8/8  15:36
 */
public interface IUserTradeService {


    /**
     * 更新用户钱包余额 扣款操作
     * @param user 扣款对象
     * @param amount 只能是正数
     * @param transTypeEnum 交易类别枚举
     * @return 交易流水号
     */
    String updateUserWallet(LiveUser user, BigDecimal amount, TransEnum.TransTypeEnum transTypeEnum, String orderNo,Integer channelId);

    /**
     * 更新用户钱包余额 扣款操作
     *
     * @return 交易流水号
     */
    String updateUserWallet(TradeSerialNumReq req);
}
