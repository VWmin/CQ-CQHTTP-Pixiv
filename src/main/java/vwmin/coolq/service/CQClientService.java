package vwmin.coolq.service;


import vwmin.coolq.entity.SendMessageEntity;


public interface CQClientService {
    void sendMessage(SendMessageEntity send);
}
