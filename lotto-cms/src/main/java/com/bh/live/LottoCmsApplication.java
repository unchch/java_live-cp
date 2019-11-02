package com.bh.live;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.bh.live",exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.bh.live.dao.*")
public class LottoCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoCmsApplication.class, args);
    }

}
