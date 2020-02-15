package vwmin.coolq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.calladapter.ObservableCallAdapterFactory;
import vwmin.coolq.network.converter.GsonConverterFactory;
import vwmin.coolq.service.networkapi.CQApi;

import java.io.IOException;

@Slf4j
@Service
public class CQClientServiceImpl implements CQClientService {
    private final CQApi api;

    public CQClientServiceImpl(BotConfig botConfig){
        NetworkClient<CQApi> client =
                new NetworkClient<>(botConfig.getCqClientUrl(), CQApi.class,
                        ObservableCallAdapterFactory.create(), GsonConverterFactory.create());
        this.api = client.getApi();
    }

    @Override
    public void sendMessage(SendMessageEntity send) {
        Assert.notNull(send, "SendMessageEntity could not be null.");

        log.info("即将发送消息 >> " + send);
        String result = "failed.";
        try {
            result = api.sendMsg(send).result();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("消息发送响应 >> " + result);
    }


}
