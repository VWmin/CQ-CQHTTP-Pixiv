package com.vwmin.coolq.pixiv;

import com.vwmin.restproxy.annotations.GET;
import com.vwmin.restproxy.annotations.Query;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 13:09
 */
public interface PixivApi {
    @GET("/illust/ranking")
    ListIllustResponse getRank(
            @Query("mode") String mode,
            @Query(value = "date", required = false) String date
    );

    @GET("/illust/detail")
    IllustResponse getIllustById(@Query("illustId") Integer id);

    @GET("/user/detail")
    UserResponse getUserById(@Query("userId") Integer id);

    @GET("/search/illust")
    ListIllustResponse getIllustByWord(
            @Query("word") String word,
            @Query("sort") String sort,
            @Query("search_type") String searchTarget
    );

    @GET("/next")
    ListIllustResponse getNext(@Query("nextUrl") String nextUrl);
}
