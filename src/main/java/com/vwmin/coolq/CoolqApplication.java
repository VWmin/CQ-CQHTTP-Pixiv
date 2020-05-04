package com.vwmin.coolq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Properties;

@SpringBootApplication
@EnableDiscoveryClient
public class CoolqApplication {

    public static void main(String[] args) {
//        Properties prop = System.getProperties();
//        prop.put("proxySet", true);
//        prop.setProperty("socksProxyHost", "127.0.0.1");
//        prop.setProperty("socksProxyPort", "9888");
//        System.setProperty("http.proxyHost", "localhost");
//        System.setProperty("http.proxyPort", "9888");
//        System.setProperty("https.proxyHost", "localhost");
//        System.setProperty("https.proxyPort", "9888");
        SpringApplication.run(CoolqApplication.class, args);
    }

}
