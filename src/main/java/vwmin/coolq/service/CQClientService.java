package vwmin.coolq.service;


import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.entity.SendMessageEntity;

import java.util.List;

public interface CQClientService {
    public void sendMessage(SendMessageEntity send);
//    public String getImage(String file);
}
