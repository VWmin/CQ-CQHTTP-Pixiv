package vwmin.coolq.function.saucenao.service;

import com.vwmin.restproxy.annotations.GET;
import com.vwmin.restproxy.annotations.Query;
import vwmin.coolq.function.saucenao.entity.SaucenaoEntity;

/**
 * 向sauceNAO发送图片搜索请求
 */
public interface SauceNAO {
    @GET("/search.php")
    SaucenaoEntity search(
            @Query("url") String url,
            @Query("database") Integer database,
            @Query("output_type") Integer outputType,
            @Query("numres") Integer numres
    );
}
