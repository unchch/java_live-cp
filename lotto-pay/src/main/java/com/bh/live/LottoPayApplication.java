package com.bh.live;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.bh.live")
@MapperScan("com.bh.live.dao*")
@EnableFeignClients
public class LottoPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoPayApplication.class, args);
    }

}
