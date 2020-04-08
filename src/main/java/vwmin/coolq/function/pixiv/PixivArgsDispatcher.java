package vwmin.coolq.function.pixiv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Component;
import vwmin.coolq.entity.*;
import vwmin.coolq.function.Command;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.core.ArgsDispatcher;
import vwmin.coolq.core.BaseService;
import vwmin.coolq.core.MessageSender;
import vwmin.coolq.service.CQClientService;
import vwmin.coolq.session.BaseSession;


/**
 * @author Min
 */
@Component("pixivArgsDispatcher")
@Slf4j
public class PixivArgsDispatcher implements ArgsDispatcher {
    private final CQClientService cqClientService;
    private final PixivService pixivService;

    public PixivArgsDispatcher(PixivService pixivService, CQClientService cqClientService){
        this.pixivService = pixivService;
        this.cqClientService = cqClientService;
    }

    @Override
    public MessageSender createSender(BaseSession session, String[] args) {
        SendMessageEntity send;
        try {
            session.setCommand(createCommand(pixivService, args));
            send = session.checkAndExecute();
        } catch (Exception e) {
            e.printStackTrace();
            send = session.handleParseException(e);
        }
        return new PixivSender(send);

    }

    @Override
    public Command createCommand(BaseService service, String[] args) throws ParseException {
        switch (args[0]){
            case "rank":
                return new RankCommand((PixivService) service, RankCommandLineParser.getInstance().parse(args));
            case "word":
                return new WordCommand((PixivService) service, WordCommandLineParser.getInstance().parse(args));
            case "next":
                return new NextCommand();
            default:
                // FIXME: 2020/2/6 return NoCommand()?
                return null;
        }
    }

    private class PixivSender implements MessageSender{
        private SendMessageEntity send;

        PixivSender(SendMessageEntity send){
            this.send = send;
        }

        @Override
        public void send() {
            cqClientService.sendMessage(send);
        }
    }
}
