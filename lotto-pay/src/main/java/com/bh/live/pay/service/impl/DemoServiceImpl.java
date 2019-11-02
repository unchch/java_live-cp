/*
package com.bh.live.pay.service.impl;

import com.bh.live.pay.consumer.AwardConsumer;
import com.bh.live.pay.service.DemoService;
import com.bh.live.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    private Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);
    @Autowired
    AwardConsumer awardConsumer;

    @Override
    public Result hello(String userName) {
        log.info("接收到的前端数据：{}",userName);
        return awardConsumer.hello(userName);
    }
}
*/
