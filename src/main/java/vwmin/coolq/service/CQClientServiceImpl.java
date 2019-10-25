package vwmin.coolq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.network.MyCallAdapterFactory;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.Response;
import vwmin.coolq.service.networkapi.CQApi;

import java.util.List;

@Slf4j
@Service
public class CQClientServiceImpl implements CQClientService {
    private final CQApi api;

    public CQClientServiceImpl(BotConfig botConfig){
        NetworkClient<CQApi> client = new NetworkClient<>(botConfig.getCqClientUrl(), CQApi.class, MyCallAdapterFactory.create());
        this.api = client.getApi();
    }

    @Override
    public void sendMessage(SendMessageEntity send) {
        Assert.notNull(send, "SendMessageEntity could not be null.");

        log.info("即将发送消息 >> " + send);

        Response<String> res = api.sendMsg(send);

        log.info("消息发送响应 >> "+res);
    }

    @Override
    public SendMessageEntity creatMessageEntity(String message_type, Long id, List<MessageSegment> messageSegments) {
        Assert.notNull(messageSegments, "MessageSegments could not be null.");
        Assert.notNull(message_type, "message_type could not be null.");
        Assert.notNull(id, "QQ id could not be null.");

        SendMessageEntity send = new SendMessageEntity();
        send.setMessage_type(message_type);
        send.setAuto_escape(true);
        send.setMessage(messageSegments);

        switch (message_type){
            case "private":
                send.setUser_id(id);
                break;
            case "group":
                send.setGroup_id(id);
                break;
            case "discuss":
                send.setDiscuss_id(id);
                break;
        }

        return send;
    }

}
