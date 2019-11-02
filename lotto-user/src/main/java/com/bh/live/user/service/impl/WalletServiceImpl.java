package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.wallet.Wallet;
import com.bh.live.user.dao.WalletDao;
import com.bh.live.user.service.IWalletService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户金额明细表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletDao, Wallet> implements IWalletService {

}
