package vwmin.coolq.service.networkapi;

import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.network.Response;
import vwmin.coolq.network.annotation.GET;
import vwmin.coolq.network.annotation.Json;
import vwmin.coolq.network.annotation.POST;
import vwmin.coolq.network.annotation.Query;

public interface CQApi {

    @POST(value = "/send_msg", contentType = "application/json")
    Response<String> sendMsg(@Json SendMessageEntity send);

//    @GET("/get_image")
//    Response<String> getImage(@Query("file") String file);

}
