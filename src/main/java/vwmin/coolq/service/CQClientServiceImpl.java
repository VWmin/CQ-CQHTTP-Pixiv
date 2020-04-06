package vwmin.coolq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.config.BotConfig;
import vwmin.coolq.entity.SendMessageEntity;

import javax.annotation.Resource;

@Slf4j
@Service
public class CQClientServiceImpl implements CQClientService {

    private final RestTemplate restTemplate;

    private final BotConfig botConfig;

    public CQClientServiceImpl(@Qualifier("normalRestTemplate") RestTemplate restTemplate, BotConfig botConfig) {
        this.restTemplate = restTemplate;
        this.botConfig = botConfig;
    }

    @Override
    public void sendMessage(SendMessageEntity send) {
        Assert.notNull(send, "SendMessageEntity could not be null.");


        String url = botConfig.getCqClientUrl() + "/send_msg";

        log.info("即将发送消息 >> " + send);
        String result = restTemplate.postForObject(url, send, String.class);
        log.info("消息发送响应 >> " + result);
    }


}
