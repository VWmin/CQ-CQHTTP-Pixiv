package vwmin.coolq.function.pixiv.service;


import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.network.annotation.*;
import vwmin.coolq.network.calladapter.Observable;

/**
 * @author Min
 */
public interface PixivApi {


    @GET("/illust/ranking")
    Observable<ListIllustResponse> getRank(@Query("mode") String mode,
                                           @Query(value = "date", required = false) String date);

    @GET("/illust/detail")
    Observable<IllustResponse> getIllustById(@Query("illust_id") String id);

    @GET("/user/detail")
    Observable<UserResponse> getUserById(@Query("userId") String id);

    @GET("/search/illust")
    Observable<ListIllustResponse> getIllustByWord(@Query("word") String word,
                                                   @Query("sort") String sort,
                                                   @Query("search_type") String searchTarget);

    @GET("/next")
    Observable<ListIllustResponse> getNext(@Query("next_url") String nextUrl);

}
