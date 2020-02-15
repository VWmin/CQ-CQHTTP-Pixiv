package vwmin.coolq.function.pixiv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.calladapter.ObservableCallAdapterFactory;
import vwmin.coolq.network.converter.GsonConverterFactory;
import vwmin.coolq.util.StringUtil;

import java.io.IOException;

@Service
@Slf4j
public class PixivServiceImpl implements PixivService{
    private final PixivApi pixivApi;



    public PixivServiceImpl(BotConfig botConfig){
        NetworkClient<PixivApi> client = new NetworkClient<>(botConfig.getPixivApi(),
                PixivApi.class, ObservableCallAdapterFactory.create(), GsonConverterFactory.create());
        this.pixivApi = client.getApi();
    }


    @Override
    public ListIllustResponse getRank(String mode, String date) throws IOException {
        return pixivApi.getRank(mode, date).result();
    }

    @Override
    public IllustResponse getIllustById(Integer illustId) throws IOException {
        return pixivApi.getIllustById(String.valueOf(illustId)).result();
    }

    @Override
    public UserResponse getUserById(Integer userId) throws IOException {
        return pixivApi.getUserById(String.valueOf(userId)).result();
    }

    @Override
    public ListIllustResponse getIllustByWord(String word, String sort, String searchTarget) throws IOException {
        ListIllustResponse result =
                pixivApi.getIllustByWord(word, sort, searchTarget).result();
        log.info("response for '/search/illust' >> "+ StringUtil.getDigest(result.toString()));
        return result;
    }

    @Override
    public ListIllustResponse getNext(String nextUrl) throws IOException {
        ListIllustResponse response = pixivApi.getNext(nextUrl).result();
        log.info("response for '/next' >> "+ StringUtil.getDigest(response.toString()));
        return response;
    }


}
