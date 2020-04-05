package vwmin.coolq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.config.BotConfig;
import vwmin.coolq.entity.SendMessageEntity;

import javax.annotation.Resource;

@Slf4j
@Service
public class CQClientServiceImpl implements CQClientService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private BotConfig botConfig;

    @Override
    public void sendMessage(SendMessageEntity send) {
        Assert.notNull(send, "SendMessageEntity could not be null.");


        String url = botConfig.getCqClientUrl() + "/send_msg";

        log.info("即将发送消息 >> " + send);
        String result = restTemplate.postForObject(url, send, String.class);
        log.info("消息发送响应 >> " + result);
    }


}
