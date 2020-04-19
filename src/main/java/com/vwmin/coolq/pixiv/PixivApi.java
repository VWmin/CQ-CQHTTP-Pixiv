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
    Illusts getRank(@Query("mode") String mode);

    @GET("/illust/detail")
    Illust getIllustById(@Query("illustId") Integer id);

    @GET("/user/detail")
    User getUserById(@Query("userId") Integer id);

    @GET("/search/illust")
    Illusts getIllustByWord(
            @Query("word") String word,
            @Query("sort") String sort,
            @Query("search_type") String searchTarget
    );

    @GET("/next")
    Illusts getNext(@Query("nextUrl") String nextUrl);

    @GET("/illust/new?restrict=all")
    Illusts getNewWorks();
}
