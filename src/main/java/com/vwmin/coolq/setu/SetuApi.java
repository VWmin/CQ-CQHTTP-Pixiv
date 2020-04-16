package com.vwmin.coolq.setu;

import com.vwmin.restproxy.annotations.GET;
import com.vwmin.restproxy.annotations.LogRequest;
import com.vwmin.restproxy.annotations.Query;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 16:35
 */
public interface SetuApi {
    @LogRequest
    @GET("/setu/")
    SetuEntity setu(
            @Query(value = "r18", required = false) int r18,
            @Query(value = "keyword", required = false) String keyword,
            @Query(value = "num", required = false) int num,
            @Query(value = "proxy", required = false) String proxy,
            @Query(value = "size1200", required = false) String size100,
            @Query(value = "apikey", required = false) String apiKey
    );
}
