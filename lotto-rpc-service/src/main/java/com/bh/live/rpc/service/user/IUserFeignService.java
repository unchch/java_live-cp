package com.bh.live.rpc.service.user;

import com.bh.live.pojo.req.live.MsgRcpReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
/**
 *@author WuLong
 *@date 2019/7/24 17:58
 *@description 用户feign调用接口暴露
 */
@FeignClient(value = "lotto-user",fallback = LiveUserServiceFallback.class)
public interface IUserFeignService {

    /**
     *
     * @author wuhuanrong
     * @param req
     * @return
     */
    @PostMapping("/user/chat-msg/send")
    Integer sendMsg(@RequestBody MsgRcpReq req);
}
