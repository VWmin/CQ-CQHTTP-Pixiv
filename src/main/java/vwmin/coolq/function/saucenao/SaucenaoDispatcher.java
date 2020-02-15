package vwmin.coolq.function.saucenao;


import org.springframework.stereotype.Component;
import vwmin.coolq.entity.BaseMessage;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.function.Command;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.function.saucenao.service.SaucenaoService;
import vwmin.coolq.service.ArgsDispatcher;
import vwmin.coolq.service.BaseService;
import vwmin.coolq.service.CQClientService;
import vwmin.coolq.service.MessageSender;
import vwmin.coolq.session.BaseSession;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("saucenaoDispatcher")
public class SaucenaoDispatcher implements ArgsDispatcher {
    private final CQClientService cqClientService;
    private final SaucenaoService saucenaoService;
    private final PixivService pixivService;

    public SaucenaoDispatcher(CQClientService cqClientService,
                              SaucenaoService saucenaoService,
                              PixivService pixivService) {
        this.cqClientService = cqClientService;
        this.saucenaoService = saucenaoService;
        this.pixivService = pixivService;
    }



    @Override
    public MessageSender createSender(BaseSession session, String[] args) {
        SendMessageEntity send;
        try {
            session.setCommand(createCommand(saucenaoService, args));
            send = session.checkAndExecute();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            send = session.handleParseException(e);
        }
        return new SaucenaoSender(send);
    }

    @Override
    public Command createCommand(BaseService service, String[] args) throws ParseException {
        String urlRegex = "https://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern compile = Pattern.compile(urlRegex);
        Matcher matcher = compile.matcher(args[1]);
        if (matcher.find()){
            String imgUrl = matcher.group();
            return new SaucenaoCommand((SaucenaoService) service, imgUrl);
        }
        throw new ParseException("没有找到图片", 1);
    }


    private class SaucenaoSender implements MessageSender{

        private SendMessageEntity send;

        SaucenaoSender(SendMessageEntity send){
            this.send = send;
        }

        @Override
        public void send() {
            if(send != null) {
                cqClientService.sendMessage(send);
            }

        }

    }
}
