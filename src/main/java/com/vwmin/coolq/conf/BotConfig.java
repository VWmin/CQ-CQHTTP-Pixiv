package com.vwmin.coolq.conf;


import com.vwmin.coolq.pixiv.PixivApi;
import com.vwmin.coolq.setu.SetuApi;
import com.vwmin.restproxy.RestProxy;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.vwmin.coolq.saucenao.SaucenaoApi;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 16:12
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "botconfig")
public class BotConfig {
    private String pixivApi;

    @Bean("microServiceTemplate")
    @LoadBalanced
    public RestTemplate microServiceTemplate(){
        return new RestTemplate();
    }

    @Bean("normalRestTemplate")
    public RestTemplate normalRestTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    @Bean
    public SetuApi setuApi(@Qualifier("normalRestTemplate") RestTemplate restTemplate){
        final String setuUrl = "https://api.lolicon.app";
        return new RestProxy<>(setuUrl, SetuApi.class, restTemplate).getApi();
    }

    @Bean
    public SaucenaoApi saucenaoApi(@Qualifier("normalRestTemplate") RestTemplate restTemplate){
        final String saucenaoUrl = "http://saucenao.com";
        return new RestProxy<>(saucenaoUrl, SaucenaoApi.class, restTemplate).getApi();
    }

    @Bean
    public PixivApi pixivApi(@Qualifier("microServiceTemplate") RestTemplate restTemplate){
        return new RestProxy<>(pixivApi, PixivApi.class, restTemplate).getApi();
    }
}
