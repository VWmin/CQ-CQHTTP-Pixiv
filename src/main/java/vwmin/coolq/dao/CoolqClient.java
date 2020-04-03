package vwmin.coolq.dao;

import vwmin.coolq.entity.SendMessageEntity;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/3 11:51
 */
public interface CoolqClient {
//    @RelativePath("/send_msg")
    void sendMsg(SendMessageEntity sendMessageEntity);
}
