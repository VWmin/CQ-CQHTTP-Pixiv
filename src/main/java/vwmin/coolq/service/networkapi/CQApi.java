package vwmin.coolq.service.networkapi;

import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.network.Response;
import vwmin.coolq.network.annotation.GET;
import vwmin.coolq.network.annotation.Json;
import vwmin.coolq.network.annotation.POST;
import vwmin.coolq.network.annotation.Query;
import vwmin.coolq.network.calladapter.Observable;

public interface CQApi {

    @POST(value = "/send_msg", contentType = "application/json")
    Observable<String> sendMsg(@Json SendMessageEntity send);


}
