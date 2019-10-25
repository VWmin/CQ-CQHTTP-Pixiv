package vwmin.coolq.function.saucenao.service;

import vwmin.coolq.function.saucenao.entity.SauceNAOEntity;
import vwmin.coolq.network.Response;
import vwmin.coolq.network.annotation.GET;
import vwmin.coolq.network.annotation.Query;

/**
 * 向sauceNAO发送图片搜索请求
 */
public interface SauceNAO {
    @GET("/search.php")
    public Response<SauceNAOEntity> search(@Query("url") String url,
                                           @Query("database") Integer database,
                                           @Query("output_type") Integer outputType,
                                           @Query("numres") Integer numres);
}
