package vwmin.coolq.function.saucenao.util;


import org.springframework.stereotype.Component;
import vwmin.coolq.entity.BaseMessage;
import vwmin.coolq.entity.HasId;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.function.saucenao.entity.SauceNAOEntity;
import vwmin.coolq.function.saucenao.service.SaucenaoService;
import vwmin.coolq.service.ArgsDispatcher;
import vwmin.coolq.service.CQClientService;
import vwmin.coolq.service.MessageSender;
import vwmin.coolq.session.BaseSession;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("saucenaoDispatcher")
public class SaucenaoDispatcher implements ArgsDispatcher {
    private final CQClientService cqClientService;
    private final SaucenaoService saucenaoService;
    private final PixivService pixivService;

    public SaucenaoDispatcher(CQClientService cqClientService, SaucenaoService saucenaoService, PixivService pixivService) {
        this.cqClientService = cqClientService;
        this.saucenaoService = saucenaoService;
        this.pixivService = pixivService;
    }


    @Override
    public MessageSender setPostMessage(BaseMessage postMessage) {
//        String urlRegex = "https://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
//        Pattern p = Pattern.compile(urlRegex);
//        Matcher m = p.matcher(postMessage.getMessage());
//        String url;
//        if(m.find()){
//            url = m.group();
//            return new SaucenaoSender(postMessage, url);
//        }else{
//            return new SaucenaoSender(postMessage, true);
//        }
        return null;

    }

    @Override
    public MessageSender setSession(BaseSession session) {
        SendMessageEntity sendMessageEntity = session.checkAndExecute(saucenaoService);
        return new SaucenaoSender(sendMessageEntity);
    }




    private class SaucenaoSender implements MessageSender{

        private SendMessageEntity send;

        SaucenaoSender(SendMessageEntity send){
            this.send = send;
        }

        @Override
        public void send() {
            if(send != null) cqClientService.sendMessage(send);

        }

        private void onSuccess(){
//            SauceNAOEntity searchResponse = saucenaoService.getSearchResponse(imageUrl, 999);
//            if(searchResponse != null && searchResponse.getResults().size()>0){
//                SaucenaoConsumer consumer = new SaucenaoConsumer(searchResponse, postMessage, pixivService);
//
//                SendMessageEntity send = new SendMessageEntity(postMessage.getMessage_type(),
//                        ((HasId) postMessage).getId(), consumer.mostly());
//
//
//
//                cqClientService.sendMessage(send);
//            }
        }


        private void onError(){
//            SendMessageEntity send = new SendMessageEntity(postMessage.getMessage_type(),
//                    ((HasId) postMessage).getId(), new MessageSegmentBuilder().addTextSegment("图片粗错啦？").build());
//
//            cqClientService.sendMessage(send);
        }



    }
}
