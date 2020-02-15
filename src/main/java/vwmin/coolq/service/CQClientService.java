package vwmin.coolq.service;


import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.entity.SendMessageEntity;

import java.util.List;

public interface CQClientService {
    void sendMessage(SendMessageEntity send);
}
