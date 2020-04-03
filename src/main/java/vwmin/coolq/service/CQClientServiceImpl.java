package vwmin.coolq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.dao.CoolqClient;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.calladapter.ObservableCallAdapterFactory;
import vwmin.coolq.network.converter.GsonConverterFactory;
import vwmin.coolq.service.networkapi.CQApi;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Service
public class CQClientServiceImpl implements CQClientService {

    @Resource
    private CoolqClient coolqClient;

    @Override
    public void sendMessage(SendMessageEntity send) {
        Assert.notNull(send, "SendMessageEntity could not be null.");
        coolqClient.sendMsg(send);
    }


}
