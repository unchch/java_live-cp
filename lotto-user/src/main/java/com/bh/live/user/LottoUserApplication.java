package com.bh.live.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.bh.live")
@MapperScan("com.bh.live.user.dao*")
@EnableFeignClients("com.bh.live.rpc.service*")
public class LottoUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoUserApplication.class, args);
    }

}
