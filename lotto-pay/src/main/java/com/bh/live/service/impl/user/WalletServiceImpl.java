package com.bh.live.service.impl.user;

import com.bh.live.model.Wallet;
import com.bh.live.dao.user.WalletDao;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.user.LoginSuccessfulStatus;
import com.bh.live.service.user.IWalletService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户金额明细表 服务实现类
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-26
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletDao, Wallet> implements IWalletService {

    @Autowired
    private WalletDao walletDao;
    @Override
    public LoginSuccessfulStatus queryLoginSuccessfulStatus(LiveUser user) {
        LoginSuccessfulStatus loginSuccessfulStatus = walletDao.queryLoginSuccessfulStatus(user);
        return loginSuccessfulStatus;
    }
}
