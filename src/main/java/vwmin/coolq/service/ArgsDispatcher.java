package vwmin.coolq.service;

import vwmin.coolq.entity.BaseMessage;
import vwmin.coolq.entity.Sender;

public interface ArgsDispatcher {
    MessageSender setPostMessage(BaseMessage postMessage);
}
