package com.bh.live.rpc.service.user;

import com.bh.live.pojo.req.live.MsgRcpReq;

/**
 *@author WuLong
 *@date 2019/7/24 20:23
 *@description 熔断回调
 */
public class LiveUserServiceFallback implements IUserFeignService{
    @Override
    public Integer sendMsg(MsgRcpReq req) {
        return -1;
    }
}
