package com.bh.live.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.bh.live")
@EnableFeignClients
public class LottoPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoPayApplication.class, args);
    }

}
