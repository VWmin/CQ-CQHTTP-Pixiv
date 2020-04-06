package vwmin.coolq.function.saucenao.service;

import com.vwmin.restproxy.RestProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.function.saucenao.entity.SaucenaoEntity;


@Slf4j
@Service
public class SaucenaoServiceImpl implements SaucenaoService{
    private static final String SAUCENAO_BASE_URL = "http://saucenao.com";
    private final SauceNAO sauceNAO;


    SaucenaoServiceImpl(@Qualifier("normalRestTemplate") RestTemplate restTemplate){
        sauceNAO = new RestProxy<>(SAUCENAO_BASE_URL, SauceNAO.class, restTemplate).getApi();
    }

    @Override
    public SaucenaoEntity getSearchResponse(String url, Integer db){
        return sauceNAO.search(url, db, 2, 3);
    }
}
