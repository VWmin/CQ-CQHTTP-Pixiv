package vwmin.coolq.function.setu.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vwmin.coolq.entity.BaseMessage;
import vwmin.coolq.entity.HasId;
import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.function.pixiv.util.PixivArgsDispatcher;
import vwmin.coolq.function.setu.entity.SetuEntity;
import vwmin.coolq.service.ArgsDispatcher;
import vwmin.coolq.service.CQClientService;
import vwmin.coolq.service.MessageSender;
import vwmin.coolq.session.BaseSession;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component("setuArgsDispatcher")
public class SetuArgsDispatcher implements ArgsDispatcher {
    private final CQClientService cqClientService;

    public SetuArgsDispatcher(CQClientService cqClientService) {
        this.cqClientService = cqClientService;
    }

    @Override
    public MessageSender setPostMessage(BaseMessage postMessage) {
        String url = "https://api.lolicon.app/setu?r18=0&num=1";
        SetuEntity res = null;
        try {
             res = HttpUtil.getInstance()
                        .newTask("get")
                        .setUrl(url)
                        .execute(SetuEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (res == null || res.getData()==null || res.getData().size() == 0) return null;

        SetuEntity.DataBean dataBean = res.getData().get(0);
        String imgUrl = dataBean.getUrl();
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        List<MessageSegment> messageSegments = builder.addAtSegment(postMessage.getUser_id())
                            .addImageSegment(dataBean.getPid()+imgUrl.substring(imgUrl.length()-4, imgUrl.length()), imgUrl)
                            .build();
        SendMessageEntity send = new SendMessageEntity(postMessage.getMessage_type(), ((HasId) postMessage).getId(), messageSegments);

        return new SetuSender(send);
    }

    @Override
    public MessageSender setSession(BaseSession session) {
        return null;
    }


    private class SetuSender implements MessageSender{

        private SendMessageEntity send;

        SetuSender(SendMessageEntity send){
            this.send = send;
        }

        @Override
        public void send() {
            cqClientService.sendMessage(send);
        }
    }
}
