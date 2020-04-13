package com.vwmin.coolq.saucenao;

import com.vwmin.restproxy.annotations.GET;
import com.vwmin.restproxy.annotations.Query;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 10:21
 */
public interface SaucenaoApi {
    @GET("/search.php")
    SaucenaoEntity search(
            @Query("url") String url,
            @Query("database") Integer database,
            @Query("output_type") Integer outputType,
            @Query("numres") Integer numres
    );
}
