package vwmin.coolq.service;

import vwmin.coolq.entity.BaseMessage;
import vwmin.coolq.entity.Sender;
import vwmin.coolq.session.BaseSession;

import java.util.Map;

public interface ArgsDispatcher {
    MessageSender setPostMessage(BaseMessage postMessage);
    MessageSender setSession(BaseSession session);
}
