package vwmin.coolq.service;

import com.vwmin.restproxy.annotations.Body;
import com.vwmin.restproxy.annotations.POST;
import vwmin.coolq.entity.SendMessageEntity;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/8 11:15
 */
public interface CQClientApi {
    @POST("/send_msg")
    String sendMsg(@Body("") SendMessageEntity send);
}
