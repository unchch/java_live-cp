package com.bh.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wulong
 * @date 2019-07-13
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaServer
public class LottoEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoEurekaServerApplication.class, args);
    }

}
