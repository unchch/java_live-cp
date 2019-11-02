/*
package com.bh.live.service.impl;

import com.bh.live.consumer.AwardConsumer;
import com.bh.live.common.result.Result;
import com.bh.live.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    AwardConsumer awardConsumer;
    private Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public Result hello(String userName) {
        log.info("接收到的前端数据：{}", userName);
        return awardConsumer.hello(userName);
    }
}
*/
