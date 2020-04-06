package vwmin.coolq.function.pixiv.service;

import com.vwmin.restproxy.RestProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.config.BotConfig;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class PixivServiceImpl implements PixivService{
    private PixivApi api;


    public PixivServiceImpl(BotConfig botConfig, @Qualifier("microServiceTemplate") RestTemplate restTemplate){
        this.api = new RestProxy<>(botConfig.getPixivApi(), PixivApi.class, restTemplate).getApi();
    }


    @Override
    public ListIllustResponse getRank(String mode, String date) {
        return api.getRank(mode, date);
    }

    @Override
    public IllustResponse getIllustById(Integer illustId) {
        return api.getIllustById(illustId);
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        return api.getUserById(userId);
    }

    @Override
    public ListIllustResponse getIllustByWord(String word, String sort, String searchTarget) throws IOException {
        return api.getIllustByWord(word, sort, searchTarget);
    }

    @Override
    public ListIllustResponse getNext(String nextUrl) {
        return api.getNext(nextUrl);
    }


}
