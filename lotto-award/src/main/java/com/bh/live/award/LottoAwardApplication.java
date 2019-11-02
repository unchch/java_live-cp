package com.bh.live.award;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *@author WuLong
 *@date 2019/8/5 19:42
 *@description 开奖工程启动入口
 */
@SpringBootApplication(scanBasePackages = "com.bh.live")
@MapperScan("com.bh.live.award.dao")
@EnableFeignClients("com.bh.live.rpc.service*")
public class LottoAwardApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoAwardApplication.class, args);
    }

}
