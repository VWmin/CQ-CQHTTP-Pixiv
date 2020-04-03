package vwmin.coolq.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.entity.SendMessageEntity;

import javax.annotation.Resource;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/3 11:47
 */
@Slf4j
@Component
public class CoolqClientImpl implements CoolqClient{
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private BotConfig botConfig;

    @Override
    public void sendMsg(SendMessageEntity send){
        String url = botConfig.getCqClientUrl() + "/send_msg";

        log.info("即将发送消息 >> " + send);
        String result = restTemplate.postForObject(url, send, String.class);
        log.info("消息发送响应 >> " + result);

    }
}
