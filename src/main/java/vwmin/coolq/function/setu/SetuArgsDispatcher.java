package vwmin.coolq.function.setu;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Component;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.function.Command;
import vwmin.coolq.function.setu.service.SetuService;
import vwmin.coolq.core.ArgsDispatcher;
import vwmin.coolq.core.BaseService;
import vwmin.coolq.service.CQClientService;
import vwmin.coolq.core.MessageSender;
import vwmin.coolq.session.BaseSession;

@Slf4j
@Component("setuArgsDispatcher")
public class SetuArgsDispatcher implements ArgsDispatcher {
    private final CQClientService cqClientService;
    private final SetuService setuService;

    public SetuArgsDispatcher(CQClientService cqClientService, SetuService setuService) {
        this.cqClientService = cqClientService;
        this.setuService = setuService;
    }

    @Override
    public MessageSender createSender(BaseSession session, String[] args) {
        SendMessageEntity send;
        try {
            session.setCommand(createCommand(setuService, args));
            send = session.checkAndExecute();
        } catch (Exception e) {
            e.printStackTrace();
            send = session.handleParseException(e);
        }
        return new SetuSender(send);
    }

    @Override
    public Command createCommand(BaseService service, String[] args) throws ParseException {
        return new SetuCommand((SetuService) service, SetuCommandLineParser.getInstance().parse(args));
    }

    private class SetuSender implements MessageSender{
        // TODO: 2020/2/5 这种设计是一种浪费，每种实现实际上做的是同一件事

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
