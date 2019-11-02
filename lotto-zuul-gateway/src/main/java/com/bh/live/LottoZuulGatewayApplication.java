package com.bh.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class LottoZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoZuulGatewayApplication.class, args);
    }

}
