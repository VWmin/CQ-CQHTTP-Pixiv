package vwmin.coolq.function.pixiv.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.config.BotConfig;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.calladapter.ObservableCallAdapterFactory;
import vwmin.coolq.network.converter.GsonConverterFactory;
import vwmin.coolq.util.StringUtil;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class PixivServiceImpl implements PixivService{
    private final RestTemplate restTemplate;
    private final BotConfig botConfig;


    public PixivServiceImpl(BotConfig botConfig, RestTemplate restTemplate){
        this.botConfig = botConfig;
        this.restTemplate = restTemplate;
    }


    @Override
    public ListIllustResponse getRank(String mode, String date) {
        String url = botConfig.getPixivApi() + "/illust/ranking";
//        URI uri = new URIBuilder().addParameter("mode", mode);
        return restTemplate.getForObject(url, ListIllustResponse.class);
    }

    @Override
    public IllustResponse getIllustById(Integer illustId) {
        String url = botConfig.getPixivApi() + "/illust/detail";
        return restTemplate.getForObject(url, IllustResponse.class);
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        String url = botConfig.getPixivApi() + "/user/detail";
        return restTemplate.getForObject(url, UserResponse.class);
    }

    @Override
    public ListIllustResponse getIllustByWord(String word, String sort, String searchTarget) throws IOException {
        String url = botConfig.getPixivApi() + "/search/illust";
        return restTemplate.getForObject(url, ListIllustResponse.class);
    }

    @Override
    public ListIllustResponse getNext(String nextUrl) {
        String url = botConfig.getPixivApi() + "/next";
        return restTemplate.getForObject(url, ListIllustResponse.class);
    }


}
