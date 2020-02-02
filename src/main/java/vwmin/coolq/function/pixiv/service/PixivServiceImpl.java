package vwmin.coolq.function.pixiv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.function.pixiv.service.networkapi.PixivApi;
import vwmin.coolq.network.MyCallAdapterFactory;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.Response;
import vwmin.coolq.util.StringUtil;

import java.sql.ResultSet;

@Service
@Slf4j
public class PixivServiceImpl implements PixivService{
    private final PixivApi pixivApi;



    PixivServiceImpl(BotConfig botConfig){
        NetworkClient<PixivApi> client = new NetworkClient<>(botConfig.getPixivApi(),
                PixivApi.class, MyCallAdapterFactory.create());
        this.pixivApi = client.getApi();
    }


    @Override
    public ListIllustResponse getRank(String mode, String date) {
        // TODO: 2019/10/16 这个要改改 太蠢了
        Response<ListIllustResponse> response = pixivApi.getRank(mode, date);
        return response.getResponse();
    }

    @Override
    public IllustResponse getIllustById(Integer illust_id) {
        Response<IllustResponse> response = pixivApi.getIllustById(illust_id+"");
        return response.getResponse();
    }

    @Override
    public UserResponse getUserById(Integer user_id) {
        Response<UserResponse> response = pixivApi.getUserById(user_id+"");
        return response.getResponse();
    }

    @Override
    public ListIllustResponse getIllustByWord(String word, String sort, String search_target) {
        Response<ListIllustResponse> response = pixivApi.getIllustByWord(word, sort, search_target);
        ListIllustResponse r = response.getResponse();
        log.info("response for '/search/illust' >> "+ StringUtil.getDigest(r.toString()));
        return r;
    }

    @Override
    public ListIllustResponse getNext(String next_url) {
        Response<ListIllustResponse> response = pixivApi.getNext(next_url);
        ListIllustResponse r = response.getResponse();
        log.info("response for '/next' >> "+ StringUtil.getDigest(r.toString()));
        return r;
    }


}
