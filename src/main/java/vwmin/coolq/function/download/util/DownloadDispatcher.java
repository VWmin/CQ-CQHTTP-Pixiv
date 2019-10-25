package vwmin.coolq.function.download.util;

import vwmin.coolq.entity.BaseMessage;
import vwmin.coolq.service.ArgsDispatcher;
import vwmin.coolq.service.MessageSender;

public class DownloadDispatcher implements ArgsDispatcher {
    @Override
    public MessageSender setPostMessage(BaseMessage postMessage) {

        return null;
    }



    class DownloadSender implements MessageSender{

        @Override
        public void send() {

        }
    }
}
