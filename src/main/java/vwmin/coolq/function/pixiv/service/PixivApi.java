package vwmin.coolq.function.pixiv.service;


import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import com.vwmin.restproxy.annotations.GET;
import com.vwmin.restproxy.annotations.Query;

/**
 * @author Min
 */
public interface PixivApi {


    @GET("/illust/ranking")
    ListIllustResponse getRank(
            @Query("mode") String mode,
            @Query(value = "date", required = false) String date
    );

    @GET("/illust/detail")
    IllustResponse getIllustById(@Query("illust_id") Integer id);

    @GET("/user/detail")
    UserResponse getUserById(@Query("userId") Integer id);

    @GET("/search/illust")
    ListIllustResponse getIllustByWord(
            @Query("word") String word,
            @Query("sort") String sort,
            @Query("search_type") String searchTarget
    );

    @GET("/next")
    ListIllustResponse getNext(@Query("next_url") String nextUrl);

}
