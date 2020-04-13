package com.vwmin.coolq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CoolqApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoolqApplication.class, args);
    }

}
