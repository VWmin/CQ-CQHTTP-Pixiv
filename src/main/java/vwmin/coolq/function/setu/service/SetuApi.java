package vwmin.coolq.function.setu.service;

import vwmin.coolq.function.setu.entity.SetuEntity;
import com.vwmin.restproxy.annotations.GET;
import com.vwmin.restproxy.annotations.Query;

public interface SetuApi {
    @GET("/setu/")
    SetuEntity setu(
            @Query(value = "r18", required = false) String r18,
            @Query(value = "keyword", required = false) String keyword,
            @Query(value = "num", required = false) String num,
            @Query(value = "proxy", required = false) String proxy,
            @Query(value = "size1200", required = false) String size100
    );
}
