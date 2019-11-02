package com.bh.live.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.user.BindingAccount;
import com.bh.live.pojo.res.user.BindingAccountRes;

import java.util.List;

/**
 * <p>
 * 用户绑定账户表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-30
 */
public interface IBindingAccountService extends IService<BindingAccount> {

    /**
     * 根据用户id获取账户列表
     * @param userId
     * @return
     */
    List<BindingAccountRes> getAccountByUserId(Integer userId);
}
