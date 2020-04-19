package com.vwmin.coolq.common;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/16 23:25
 */
@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    private final
    RedisTemplate<String, Object> autoRedisTemplate;

    public RedisUtil(RedisTemplate<String, Object> autoRedisTemplate) {
        this.autoRedisTemplate = autoRedisTemplate;
    }

    @PostConstruct
    private void init(){
        redisTemplate = autoRedisTemplate;
    }


    public static void add2Set(String key, Object... values) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, values);
    }

    public static Boolean isMember(String key, Object o) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.isMember(key, o);
    }
}
