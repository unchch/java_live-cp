package com.bh.live.shiro.provider;


import com.bh.live.model.cms.AuthUser;

/**
 * 数据库用户密码账户提供者接口
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public interface IAccountProvider {

    /**
     * 提供账户数据库用户密码
     * @param uid
     * @return
     */
    AuthUser selectByUid(String uid);

}
