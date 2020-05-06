package com.vwmin.coolq.setu;

import com.vwmin.terminalservice.CQClientApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/5/5 12:04
 */
@Slf4j
//@Service
public class AutoDeleteMsg extends KeyExpirationEventMessageListener {

    final static String CACHE = "delete";

    private final
    CQClientApi cqClientApi;

    public AutoDeleteMsg(RedisMessageListenerContainer listenerContainer, CQClientApi cqClientApi) {
        super(listenerContainer);
        this.cqClientApi = cqClientApi;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
//        String redisKey = new String(message.getBody());
//        log.info("expired key >>> " + redisKey);
//        String[] keyAndCache = redisKey.split(":");
//        if (keyAndCache.length == 2 && keyAndCache[0].equals(CACHE)){
//            String key = keyAndCache[1];
//            cqClientApi.deleteMsg(Integer.parseInt(key));
//        }
//
//        super.onMessage(message, pattern);
    }
}
