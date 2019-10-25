package vwmin.coolq.service;


import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.entity.SendMessageEntity;

import java.util.List;

public interface CQClientService {
    public void sendMessage(SendMessageEntity send);
    public SendMessageEntity creatMessageEntity(String message_type, Long id, List<MessageSegment> messageSegments);
//    public String getImage(String file);
}
