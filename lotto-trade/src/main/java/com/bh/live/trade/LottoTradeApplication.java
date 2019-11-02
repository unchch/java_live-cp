package com.bh.live.trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.bh.live")
@MapperScan("com.bh.live.trade.dao")
@EnableFeignClients("com.bh.live.rpc.service*")
public class LottoTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoTradeApplication.class, args);
    }

}
