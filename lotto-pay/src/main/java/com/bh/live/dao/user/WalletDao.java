package com.bh.live.dao.user;

import com.bh.live.model.Wallet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.user.LoginSuccessfulStatus;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户金额明细表 Mapper 接口
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-26
 */
public interface WalletDao extends BaseMapper<Wallet> {

    /**
     * 登陆用户状态
     * @param user userid
     * @return 用户状态信息
     */
    LoginSuccessfulStatus queryLoginSuccessfulStatus( @Param("user") LiveUser user);
}
