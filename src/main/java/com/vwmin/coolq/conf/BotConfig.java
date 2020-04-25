package com.vwmin.coolq.conf;


import com.vwmin.coolq.honkar3rd.ResourceManager;
import com.vwmin.coolq.honkar3rd.gacha.CharacterSupply;
import com.vwmin.coolq.honkar3rd.gacha.EquipmentSupply;
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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.vwmin.coolq.saucenao.SaucenaoApi;

import java.io.IOException;
import java.util.prefs.BackingStoreException;

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
    private String setuKey;

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
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 设置value的序列化规则和key的序列化规则
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(serializer);
        template.setDefaultSerializer(serializer);
        template.afterPropertiesSet();
        return template;
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

    @Bean("focusedSupplyA")
    public EquipmentSupply focusedSupplyA(ResourceManager manager) throws IOException, BackingStoreException {
        return new EquipmentSupply(manager, EquipmentSupply.FOCUSED_A);
    }

    @Bean("focusedSupplyB")
    public EquipmentSupply focusedSupplyB(ResourceManager manager) throws IOException, BackingStoreException {
        return new EquipmentSupply(manager, EquipmentSupply.FOCUSED_B);
    }

    @Bean("expansionSupplyA")
    public CharacterSupply expansionSupplyA(ResourceManager manager) throws IOException, BackingStoreException {
        return new CharacterSupply(manager);
    }

    @Bean("expansionSupplyB")
    public EquipmentSupply expansionSupplyB(ResourceManager manager) throws IOException, BackingStoreException {
        return new EquipmentSupply(manager, EquipmentSupply.EXPANSION_B);
    }
}
