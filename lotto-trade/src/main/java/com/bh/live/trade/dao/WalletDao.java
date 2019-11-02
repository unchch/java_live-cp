package com.bh.live.trade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.trade.Wallet;


/**
 * <p>
 * 用户金额明细表 Mapper 接口
 * </p>
 *
 * @author lgs
 * @since 2019-08-08
 */
public interface WalletDao extends BaseMapper<Wallet> {

    int updateUserAmount(Wallet wallet);

}
