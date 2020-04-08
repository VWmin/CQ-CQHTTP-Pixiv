package vwmin.coolq.core;

import org.apache.commons.cli.ParseException;
import vwmin.coolq.core.BaseService;
import vwmin.coolq.core.MessageSender;
import vwmin.coolq.function.Command;
import vwmin.coolq.session.BaseSession;


public interface ArgsDispatcher {
    MessageSender createSender(BaseSession session, String[] args);
    Command createCommand(BaseService service, String[] args) throws ParseException, java.text.ParseException;
}
