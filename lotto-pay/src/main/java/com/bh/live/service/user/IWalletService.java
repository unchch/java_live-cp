package com.bh.live.service.user;

import com.bh.live.model.Wallet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.user.LoginSuccessfulStatus;

/**
 * <p>
 * 用户金额明细表 服务类
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-26
 */
public interface IWalletService extends IService<Wallet> {

    /**
     * 登陆用户状态
     * @param user userid
     * @return 用户状态信息
     */
    LoginSuccessfulStatus queryLoginSuccessfulStatus(LiveUser user);
}
