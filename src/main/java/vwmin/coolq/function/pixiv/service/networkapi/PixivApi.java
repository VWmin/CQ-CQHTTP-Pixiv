package vwmin.coolq.function.pixiv.service.networkapi;


import org.springframework.web.bind.annotation.GetMapping;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.LoginResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.network.Response;
import vwmin.coolq.network.annotation.*;

public interface PixivApi {
    @GET("/illust/ranking")
    public Response<ListIllustResponse> getRank(@Query("mode") String mode, @Query("date") String date);

    @GET("/illust/detail")
    public Response<IllustResponse> getIllustById(@Query("illust_id") String id);

    @GET("/user/detail")
    public Response<UserResponse> getUserById(@Query("user_id") String id);

    @GET("/search/illust")
    public Response<ListIllustResponse> getIllustByWord(@Query("word") String word, @Query("sort") String sort, @Query("search_type") String search_target);

    @GET("/next")
    public Response<ListIllustResponse> getNext(@Query("next_url") String next_url);

}
